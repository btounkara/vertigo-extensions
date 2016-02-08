/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
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
package io.vertigo.x.comment;

import io.vertigo.app.Home;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Builder;
import io.vertigo.util.DateUtil;
import io.vertigo.x.account.Account;
import io.vertigo.x.account.AccountManager;

import java.util.Date;
import java.util.UUID;

/**
 * @author pchretien
 */
public final class CommentBuilder implements Builder<Comment> {
	private final UUID uuid;
	private String myMsg;
	private URI<Account> myAuthor;
	private String myAuthorDisplayName;
	private Date myCreationDate;
	private Date myLastModified;

	/**
	 * Constructor for new comment.
	 */
	public CommentBuilder() {
		uuid = UUID.randomUUID();
	}

	/**
	 * Constructor for new comment.
	 * @param uuid Uuid Comment unique id
	 * @param author Author
	 * @param creationDate Creation date
	 */
	public CommentBuilder(final UUID uuid, final URI<Account> author, final Date creationDate) {
		this.uuid = uuid;
		myAuthor = author;
		myAuthorDisplayName = getAccountManager().getStore().getAccount(author).getDisplayName();
		myCreationDate = creationDate;
	}

	private static AccountManager getAccountManager() {
		return Home.getApp().getComponentSpace().resolve(AccountManager.class);
	}

	/**
	 * @param author Author account
	 * @return this builder
	 */
	public CommentBuilder withAuthor(final URI<Account> author) {
		Assertion.checkArgument(myAuthor == null, "author already set");
		Assertion.checkNotNull(author);
		//-----
		myAuthor = author;
		myAuthorDisplayName = getAccountManager().getStore().getAccount(author).getDisplayName();
		return this;
	}

	/**
	 * @param msg Comment
	 * @return this builder
	 */
	public CommentBuilder withMsg(final String msg) {
		Assertion.checkArgument(myMsg == null, "msg already set");
		Assertion.checkArgNotEmpty(msg);
		//-----
		myMsg = msg;
		return this;
	}

	/**
	 * @param creationDate create date time
	 * @return this builder
	 */
	public CommentBuilder withCreationDate(final Date creationDate) {
		Assertion.checkArgument(myCreationDate == null, "creationDate already set");
		Assertion.checkNotNull(creationDate);
		//-----
		myCreationDate = creationDate;
		return this;
	}

	/**
	 * @param lastModified Last modify date time
	 * @return this builder
	 */
	public CommentBuilder withLastModified(final Date lastModified) {
		Assertion.checkArgument(myLastModified == null, "lastModified already set");
		//lastModified is optional
		//-----
		myLastModified = lastModified;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public Comment build() {
		if (myCreationDate == null) {
			myCreationDate = DateUtil.newDateTime();
		}
		return new Comment(uuid, myAuthor, myAuthorDisplayName, myMsg, myCreationDate, myLastModified);
	}
}
