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

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.annotation.StyleRes
import androidx.core.app.ActivityCompat
import com.kuloud.android.permission.RationaleDialogFragment

/**
 * Permissions helper for [Activity].
 */
internal class ActivityPermissionHelper(host: Activity) : PermissionHelper<Activity>(host) {
    override fun directRequestPermissions(requestCode: Int, vararg perms: String) {
        ActivityCompat.requestPermissions(host, perms, requestCode)
    }

    override fun shouldShowRequestPermissionRationale(perm: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(host, perm)
    }

    override val context: Context
        get() = host

    override fun showRequestPermissionRationale(
        rationale: String,
        positiveButton: String,
        negativeButton: String,
        @StyleRes theme: Int,
        requestCode: Int,
        vararg perms: String
    ) {
        val fm = host.fragmentManager

        // Check if fragment is already showing
        val fragment = fm.findFragmentByTag(RationaleDialogFragment.TAG)
        if (fragment is RationaleDialogFragment) {
            Log.d(TAG, "Found existing fragment, not showing rationale.")
            return
        }
        RationaleDialogFragment
            .newInstance(positiveButton, negativeButton, rationale, theme, requestCode, perms)
            .showAllowingStateLoss(fm, RationaleDialogFragment.TAG)
    }

    companion object {
        private const val TAG = "ActPermissionHelper"
    }
}