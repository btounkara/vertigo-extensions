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
package io.vertigo.orchestra.domain.definition;

import io.vertigo.dynamo.domain.model.Entity;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.model.VAccessor;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.lang.Generated;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
@Generated
@io.vertigo.dynamo.domain.stereotype.DataSpace("orchestra")
public final class OActivity implements Entity {
	private static final long serialVersionUID = 1L;

	private Long actId;
	private String name;
	private String label;
	private Integer number;
	private Boolean milestone;
	private String engine;

	@io.vertigo.dynamo.domain.stereotype.Association(
			name = "A_ACT_PRO",
			fkFieldName = "PRO_ID",
			primaryDtDefinitionName = "DT_O_PROCESS",
			primaryIsNavigable = true,
			primaryRole = "Process",
			primaryLabel = "Processus",
			primaryMultiplicity = "1..1",
			foreignDtDefinitionName = "DT_O_ACTIVITY",
			foreignIsNavigable = false,
			foreignRole = "Activity",
			foreignLabel = "Activity",
			foreignMultiplicity = "0..*")
	private final VAccessor<io.vertigo.orchestra.domain.definition.OProcess> proIdAccessor = new VAccessor<>(io.vertigo.orchestra.domain.definition.OProcess.class, "Process");

	/** {@inheritDoc} */
	@Override
	public URI<OActivity> getURI() {
		return DtObjectUtil.createURI(this);
	}
	
	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'Id Activité'.
	 * @return Long actId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_O_IDENTIFIANT", type = "ID", required = true, label = "Id Activité")
	public Long getActId() {
		return actId;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'Id Activité'.
	 * @param actId Long <b>Obligatoire</b>
	 */
	public void setActId(final Long actId) {
		this.actId = actId;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Nom de l'activité'.
	 * @return String name
	 */
	@Field(domain = "DO_O_LIBELLE", label = "Nom de l'activité")
	public String getName() {
		return name;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Nom de l'activité'.
	 * @param name String
	 */
	public void setName(final String name) {
		this.name = name;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Libellé de l'activité'.
	 * @return String label
	 */
	@Field(domain = "DO_O_LIBELLE", label = "Libellé de l'activité")
	public String getLabel() {
		return label;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Libellé de l'activité'.
	 * @param label String
	 */
	public void setLabel(final String label) {
		this.label = label;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Numéro de l'activité'.
	 * @return Integer number
	 */
	@Field(domain = "DO_O_NOMBRE", label = "Numéro de l'activité")
	public Integer getNumber() {
		return number;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Numéro de l'activité'.
	 * @param number Integer
	 */
	public void setNumber(final Integer number) {
		this.number = number;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Jalon'.
	 * @return Boolean milestone
	 */
	@Field(domain = "DO_O_BOOLEEN", label = "Jalon")
	public Boolean getMilestone() {
		return milestone;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Jalon'.
	 * @param milestone Boolean
	 */
	public void setMilestone(final Boolean milestone) {
		this.milestone = milestone;
	}
	
	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Implémentation de l'activité'.
	 * @return String engine
	 */
	@Field(domain = "DO_O_CLASSE", label = "Implémentation de l'activité")
	public String getEngine() {
		return engine;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Implémentation de l'activité'.
	 * @param engine String
	 */
	public void setEngine(final String engine) {
		this.engine = engine;
	}
	
	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'Processus'.
	 * @return Long proId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_O_IDENTIFIANT", type = "FOREIGN_KEY", required = true, label = "Processus")
	public Long getProId() {
		return (Long)  proIdAccessor.getId();
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'Processus'.
	 * @param proId Long <b>Obligatoire</b>
	 */
	public void setProId(final Long proId) {
		proIdAccessor.setId(proId);
	}

 	/**
	 * Association : Processus.
	 * @return l'accesseur vers la propriété 'Processus'
	 */
	public VAccessor<io.vertigo.orchestra.domain.definition.OProcess> process() {
		return proIdAccessor;
	}
	
	@Deprecated
	public io.vertigo.orchestra.domain.definition.OProcess getProcess() {
		// we keep the lazyness
		if (!proIdAccessor.isLoaded()) {
			proIdAccessor.load();
		}
		return proIdAccessor.get();
	}

	/**
	 * Retourne l'URI: Processus.
	 * @return URI de l'association
	 */
	@Deprecated
	public io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.definition.OProcess> getProcessURI() {
		return proIdAccessor.getURI();
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
