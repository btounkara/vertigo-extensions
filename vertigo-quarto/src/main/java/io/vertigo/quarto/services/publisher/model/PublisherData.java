/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013-2018, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
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
package io.vertigo.quarto.services.publisher.model;

import io.vertigo.lang.Assertion;
import io.vertigo.quarto.services.publisher.metamodel.PublisherDataDefinition;

/**
 * Données à fusionner.
 *
 * @author npiedeloup
 */
public final class PublisherData {
	private final PublisherDataDefinition publisherDataDefinition;
	private final PublisherNode root;

	/**
	 * Constructor.
	 * @param dataDefinition Definition des données de publication
	 */
	public PublisherData(final PublisherDataDefinition dataDefinition) {
		Assertion.checkNotNull(dataDefinition);
		//-----
		publisherDataDefinition = dataDefinition;
		root = new PublisherNode(dataDefinition.getRootNodeDefinition());
	}

	/**
	 * @return Définition des données.
	 */
	public PublisherDataDefinition getDefinition() {
		return publisherDataDefinition;
	}

	/**
	 * @return Noeud racine
	 */
	public PublisherNode getRootNode() {
		return root;
	}
}
