/*
 * Copyright (c) 2017. tangzx(love.tangzx@qq.com)
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

package com.tang.intellij.lua.debugger.unity

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.execution.configurations.ConfigurationTypeUtil
import com.tang.intellij.lua.UnityIcons

import javax.swing.*

/**
 *
 * Created by Taigacon on 2018/11/6.
 */
class LuaUnityConfigurationType : ConfigurationType {

    private val factory = LuaUnityConfigurationFactory(this)

    override fun getDisplayName(): String {
        return "Lua Attach Unity"
    }

    override fun getConfigurationTypeDescription(): String {
        return "Lua Attach Debugger"
    }

    override fun getIcon(): Icon {
        return UnityIcons.UNITY
    }

    override fun getId(): String {
        return "lua.debug.unity"
    }

    override fun getConfigurationFactories(): Array<ConfigurationFactory> {
        return arrayOf(factory)
    }

    companion object {

        val instance: LuaUnityConfigurationType
            get() = ConfigurationTypeUtil.findConfigurationType(LuaUnityConfigurationType::class.java)
    }
}
