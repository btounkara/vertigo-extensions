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
package io.vertigo.rules.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author xdurand
 *
 */
public final class RuleConstants {

	private final Map<String, String> constants = new ConcurrentHashMap<>();

	/**
	 * Empty rule constants
	 */
	public static final RuleConstants EMPTY_RULE_CONSTANTS = new RuleConstants();

	/**
	 *
	 * @param key
	 * @param value
	 */
	public void addConstant(final String key, final String value) {
		constants.put(key, value);
	}

	/**
	 *
	 * @return list of constants
	 */
	public List<Map.Entry<String, String>> getValues() {
		return new ArrayList<>(constants.entrySet());
	}

}
