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
package io.vertigo.quarto.plugins.converter.openoffice;

import java.io.File;
import java.util.Locale;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.star.beans.PropertyValue;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.ucb.XFileIdentifierConverter;
import com.sun.star.uno.UnoRuntime;

import io.vertigo.dynamo.file.FileManager;
import io.vertigo.lang.Assertion;

/**
 * Conversion des fichiers à partir de OpenOffice.
 * @author npiedeloup
 */
public final class OpenOfficeLocalConverterPlugin extends AbstractOpenOfficeConverterPlugin {
	private static final Logger LOGGER = LogManager.getLogger(OpenOfficeLocalConverterPlugin.class);

	/**
	 * Constructeur.
	 * @param fileManager Manager de gestion des fichiers
	 * @param unoPort Port de connexion au serveur OpenOffice
	 * @param convertTimeoutSeconds Timeout de conversion des documents
	 */
	@Inject
	public OpenOfficeLocalConverterPlugin(
			final FileManager fileManager,
			@Named("unoport") final String unoPort,
			@Named("convertTimeoutSeconds") final Optional<Integer> convertTimeoutSeconds) {
		super(fileManager, "localhost", unoPort, convertTimeoutSeconds.orElse(60));

	}

	/** {@inheritDoc} */
	@Override
	protected void storeDocument(final File outputFile, final XComponent xDoc, final ConverterFormat targetFormat, final OpenOfficeConnection openOfficeConnection) throws Exception {
		final XFileIdentifierConverter fileContentProvider = openOfficeConnection.getFileContentProvider();
		final String outputUrl = fileContentProvider.getFileURLFromSystemPath("", outputFile.getAbsolutePath());
		final PropertyValue[] storeProps = getFileProperties(targetFormat);
		final XStorable xStorable = UnoRuntime.queryInterface(XStorable.class, xDoc);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Storing to " + outputUrl);
		}
		xStorable.storeToURL(outputUrl, storeProps);
	}

	/** {@inheritDoc} */
	@Override
	protected XComponent loadDocument(final File inputFile, final OpenOfficeConnection openOfficeConnection) throws Exception {
		final XFileIdentifierConverter fileContentProvider = openOfficeConnection.getFileContentProvider();
		final String inputUrl = fileContentProvider.getFileURLFromSystemPath("", inputFile.getAbsolutePath());

		final String inputExtensionStr = inputUrl.substring(inputUrl.lastIndexOf('.') + 1).toUpperCase(Locale.ENGLISH);
		final ConverterFormat inputDocType = ConverterFormat.valueOf(inputExtensionStr);
		final PropertyValue[] loadProps = getFileProperties(inputDocType);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Openning document... " + inputUrl);
		}
		final XComponent xDoc = openOfficeConnection.getDesktop().loadComponentFromURL(inputUrl, "_blank", 0, loadProps);
		Assertion.checkNotNull(xDoc, "Le document n''a pas été chargé : {0}", inputUrl);

		return xDoc;
	}
}
