/*
 * Copyright 2020 Kuloud
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

package com.kuloud.android.permission.helper

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Permissions helper for [Fragment] from the support library.
 */
internal class SupportFragmentPermissionHelper(host: Fragment) :
    BaseSupportPermissionsHelper<Fragment>(host) {
    override fun getSupportFragmentManager(): FragmentManager {
        return host.childFragmentManager
    }

    override fun directRequestPermissions(requestCode: Int, vararg perms: String) {
        host.requestPermissions(perms, requestCode)
    }

    override fun shouldShowRequestPermissionRationale(perm: String): Boolean {
        return host.shouldShowRequestPermissionRationale(perm)
    }

    override val context: Context?
        get() = host.activity
}