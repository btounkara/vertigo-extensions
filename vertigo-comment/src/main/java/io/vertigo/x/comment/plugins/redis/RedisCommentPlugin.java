/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013-2017, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
 * KleeGroup, Centre d'affaire la Boursidiere - BP 159 - 92357 Le Plessis Robinson Cedex - France
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vertigo.x.comment.plugins.redis;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import io.vertigo.commons.impl.connectors.redis.RedisConnector;
import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.model.KeyConcept;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.WrappedException;
import io.vertigo.util.MapBuilder;
import io.vertigo.x.account.services.Account;
import io.vertigo.x.comment.impl.CommentPlugin;
import io.vertigo.x.comment.services.Comment;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

/**
 * @author pchretien
 */
public final class RedisCommentPlugin implements CommentPlugin {
	private static final String CODEC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	private final RedisConnector redisConnector;

	/**
	 * @param redisConnector Redis connector
	 */
	@Inject
	public RedisCommentPlugin(final RedisConnector redisConnector) {
		Assertion.checkNotNull(redisConnector);
		//-----
		this.redisConnector = redisConnector;
	}

	/** {@inheritDoc} */
	@Override
	public <S extends KeyConcept> void publish(final Comment comment, final URI<S> keyConceptUri) {
		try (final Jedis jedis = redisConnector.getResource()) {
			try (final Transaction tx = jedis.multi()) {
				tx.hmset("comment:" + comment.getUuid(), toMap(comment));
				tx.lpush("comments:" + keyConceptUri.urn(), comment.getUuid().toString());
				tx.exec();
			} catch (final IOException e) {
				throw WrappedException.wrap(e, "Can't publish comment onto {0}", keyConceptUri.urn());
			}
		}

	}

	/** {@inheritDoc} */
	@Override
	public void update(final Comment comment) {
		try (final Jedis jedis = redisConnector.getResource()) {
			//On vérifie la présence de l'élément en base pour s'assurer la cohérence du stockage,
			//et notament qu'il soit référencé dans "comments:keyConceptUrn"
			final boolean elementExist = jedis.exists("comment:" + comment.getUuid());
			if (!elementExist) {
				throw new UnsupportedOperationException("Comment " + comment.getUuid() + " doesn't exists");
			}
			jedis.hmset("comment:" + comment.getUuid(), toMap(comment));
		}
	}

	/** {@inheritDoc} */
	@Override
	public Comment get(final UUID uuid) {
		try (final Jedis jedis = redisConnector.getResource()) {
			return fromMap(jedis.hgetAll("comment:" + uuid));
		}
	}

	/** {@inheritDoc} */
	@Override
	public <S extends KeyConcept> List<Comment> getComments(final URI<S> keyConceptUri) {
		final List<Response<Map<String, String>>> responses = new ArrayList<>();
		try (final Jedis jedis = redisConnector.getResource()) {
			final List<String> uuids = jedis.lrange("comments:" + keyConceptUri.urn(), 0, -1);
			try (final Transaction tx = jedis.multi()) {
				for (final String uuid : uuids) {
					responses.add(tx.hgetAll("comment:" + uuid));
				}
				tx.exec();
			} catch (final IOException e) {
				throw WrappedException.wrap(e);
			}
		}
		//----- we are using tx to avoid roundtrips
		final List<Comment> comments = new ArrayList<>();
		for (final Response<Map<String, String>> response : responses) {
			final Map<String, String> data = response.get();
			if (!data.isEmpty()) {
				comments.add(fromMap(data));
			}
		}
		return comments;
	}

	private static Map<String, String> toMap(final Comment comment) {
		final String creationDate = new SimpleDateFormat(CODEC_DATE_FORMAT).format(comment.getCreationDate());
		final String lastModified = comment.getLastModified() != null ? new SimpleDateFormat(CODEC_DATE_FORMAT).format(comment.getLastModified()) : null;
		return new MapBuilder<String, String>()
				.put("uuid", comment.getUuid().toString())
				.put("author", String.valueOf(comment.getAuthor().getId()))
				.put("msg", comment.getMsg())
				.put("creationDate", creationDate)
				.putNullable("lastModified", lastModified)
				.build();
	}

	private static Comment fromMap(final Map<String, String> data) {
		try {
			final DtDefinition dtDefinition = DtObjectUtil.findDtDefinition(Account.class);
			final Date creationDate = new SimpleDateFormat(CODEC_DATE_FORMAT).parse(data.get("creationDate"));
			final Date lastModified = data.get("lastModified") != null ? new SimpleDateFormat(CODEC_DATE_FORMAT).parse(data.get("lastModified")) : null;

			return Comment.builder()
					.withUuid(UUID.fromString(data.get("uuid")))
					.withAuthor(new URI<Account>(dtDefinition, data.get("author")))
					.withCreationDate(creationDate)
					.withMsg(data.get("msg"))
					.withLastModified(lastModified)
					.build();

		} catch (final ParseException e) {
			throw WrappedException.wrap(e, "Can't parse comment");
		}
	}
}
