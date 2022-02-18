/*
 *  Copyright (c) 2022  Thiago Lopes da Silva
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.lopes.githubsearch.util.rule

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@ExperimentalCoroutinesApi
class TestCoroutineRule : TestRule {

    // this instance could be used to inject Coroutine dispatcher on ViewModel scope
    val testDispatcher = TestCoroutineDispatcher()

    // 1. this instance could be used to run blockingTest on unit testing
    // 2. the second form to run blocking test is using extension function runBlockingTest as described below
    val testCoroutineScope = TestCoroutineScope(testDispatcher)

    override fun apply(base: Statement?, description: Description?): Statement =
        object : Statement() {
            override fun evaluate() {
                kotlinx.coroutines.Dispatchers.setMain(testDispatcher)

                base?.evaluate()

                kotlinx.coroutines.Dispatchers.resetMain()
                testCoroutineScope.cleanupTestCoroutines()
            }
        }

    /**
     * Run blocking Test by provided Coroutine Scope
     * @param block code under test
     */
    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        testCoroutineScope.runBlockingTest { block() }
}