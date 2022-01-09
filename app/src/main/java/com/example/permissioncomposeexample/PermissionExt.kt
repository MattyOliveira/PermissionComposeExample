package com.example.permissioncomposeexample

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@ExperimentalPermissionsApi
fun PermissionState.IsPermanentDenied() = !this.hasPermission && !this.shouldShowRationale