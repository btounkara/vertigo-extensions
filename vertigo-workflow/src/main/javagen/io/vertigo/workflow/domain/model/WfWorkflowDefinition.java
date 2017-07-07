package io.vertigo.workflow.domain.model;

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
public final class WfWorkflowDefinition implements Entity {
	private static final long serialVersionUID = 1L;

	private Long wfwdId;
	private String name;
	private java.util.Date date;
	private final VAccessor<io.vertigo.workflow.domain.model.WfActivityDefinition> wfadIdAccessor = new VAccessor<>(io.vertigo.workflow.domain.model.WfActivityDefinition.class, "startActivity");
	private io.vertigo.dynamo.domain.model.DtList<io.vertigo.workflow.domain.model.WfTransitionDefinition> wfTransitionDefinition;

	/** {@inheritDoc} */
	@Override
	public URI<WfWorkflowDefinition> getURI() {
		return DtObjectUtil.createURI(this);
	}

	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'Id Workflow definition'.
	 * @return Long wfwdId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_WF_ID", type = "ID", required = true, label = "Id Workflow definition")
	public Long getWfwdId() {
		return wfwdId;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'Id Workflow definition'.
	 * @param wfwdId Long <b>Obligatoire</b>
	 */
	public void setWfwdId(final Long wfwdId) {
		this.wfwdId = wfwdId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'name'.
	 * @return String name
	 */
	@Field(domain = "DO_WF_LABEL", label = "name")
	public String getName() {
		return name;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'name'.
	 * @param name String
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'date'.
	 * @return java.util.Date date
	 */
	@Field(domain = "DO_WF_DATE", label = "date")
	public java.util.Date getDate() {
		return date;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'date'.
	 * @param date java.util.Date
	 */
	public void setDate(final java.util.Date date) {
		this.date = date;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'startActivity'.
	 * @return Long wfadId
	 */
	@Field(domain = "DO_WF_ID", type = "FOREIGN_KEY", label = "startActivity")
	public Long getWfadId() {
		return (Long)  wfadIdAccessor.getId();
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'startActivity'.
	 * @param wfadId Long
	 */
	public void setWfadId(final Long wfadId) {
		wfadIdAccessor.setId(wfadId);
	}

	/**
	 * Association : startActivity.
	 * @return io.vertigo.workflow.domain.model.WfActivityDefinition
	 */
	public io.vertigo.workflow.domain.model.WfActivityDefinition getStartActivity() {
		return wfadIdAccessor.get();
	}

	/**
	 * Retourne l'URI: startActivity.
	 * @return URI de l'association
	 */
	@io.vertigo.dynamo.domain.stereotype.Association(
			name = "A_WFWD_WFAD",
			fkFieldName = "WFAD_ID",
			primaryDtDefinitionName = "DT_WF_ACTIVITY_DEFINITION",
			primaryIsNavigable = true,
			primaryRole = "StartActivity",
			primaryLabel = "startActivity",
			primaryMultiplicity = "0..1",
			foreignDtDefinitionName = "DT_WF_WORKFLOW_DEFINITION",
			foreignIsNavigable = false,
			foreignRole = "WfWorkflowDefinition",
			foreignLabel = "WfWorkflowDefinition",
			foreignMultiplicity = "0..*")
	public io.vertigo.dynamo.domain.model.URI<io.vertigo.workflow.domain.model.WfActivityDefinition> getStartActivityURI() {
		return wfadIdAccessor.getURI();
	}

	/**
	 * Association : WfTransitionDefinition.
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.workflow.domain.model.WfTransitionDefinition>
	 */
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.workflow.domain.model.WfTransitionDefinition> getWfTransitionDefinitionList() {
		// On doit avoir une clé primaire renseignée. Si ce n'est pas le cas, on renvoie une liste vide
		if (io.vertigo.dynamo.domain.util.DtObjectUtil.getId(this) == null) {
			return new io.vertigo.dynamo.domain.model.DtList<>(io.vertigo.workflow.domain.model.WfTransitionDefinition.class);
		}
		final io.vertigo.dynamo.domain.model.DtListURI fkDtListURI = getWfTransitionDefinitionDtListURI();
		io.vertigo.lang.Assertion.checkNotNull(fkDtListURI);
		//---------------------------------------------------------------------
		//On est toujours dans un mode lazy.
		if (wfTransitionDefinition == null) {
			wfTransitionDefinition = io.vertigo.app.Home.getApp().getComponentSpace().resolve(io.vertigo.dynamo.store.StoreManager.class).getDataStore().findAll(fkDtListURI);
		}
		return wfTransitionDefinition;
	}

	/**
	 * Association URI: WfTransitionDefinition.
	 * @return URI de l'association
	 */
	@io.vertigo.dynamo.domain.stereotype.Association(
			name = "A_WFWD_WFTD",
			fkFieldName = "WFWD_ID",
			primaryDtDefinitionName = "DT_WF_WORKFLOW_DEFINITION",
			primaryIsNavigable = false,
			primaryRole = "WfWorkflowDefinition",
			primaryLabel = "WfWorkflowDefinition",
			primaryMultiplicity = "0..1",
			foreignDtDefinitionName = "DT_WF_TRANSITION_DEFINITION",
			foreignIsNavigable = true,
			foreignRole = "WfTransitionDefinition",
			foreignLabel = "WfTransitionDefinition",
			foreignMultiplicity = "0..*")
	public io.vertigo.dynamo.domain.metamodel.association.DtListURIForSimpleAssociation getWfTransitionDefinitionDtListURI() {
		return io.vertigo.dynamo.domain.util.DtObjectUtil.createDtListURIForSimpleAssociation(this, "A_WFWD_WFTD", "WfTransitionDefinition");
	}


	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}