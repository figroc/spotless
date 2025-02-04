/*
 * Copyright 2016-2021 DiffPlug
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diffplug.gradle.spotless;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ScalaExtensionTest extends GradleIntegrationHarness {
	@Test
	void integration() throws IOException {
		setFile("build.gradle").toLines(
				"buildscript { repositories { mavenCentral() } }",
				"plugins {",
				"    id 'com.diffplug.spotless'",
				"}",
				"apply plugin: 'scala'",
				"spotless {",
				"    scala {",
				"        scalafmt().configFile('scalafmt.conf')",
				"    }",
				"}");
		setFile("scalafmt.conf").toResource("scala/scalafmt/scalafmt.conf");
		setFile("src/main/scala/basic.scala").toResource("scala/scalafmt/basic.dirty");
		gradleRunner().withArguments("spotlessApply").build();
		assertFile("src/main/scala/basic.scala").sameAsResource("scala/scalafmt/basic.cleanWithCustomConf_3.0.0");
	}
}
