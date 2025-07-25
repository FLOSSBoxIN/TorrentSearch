package com.prajwalch.torrentsearch.ui.screens.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

import com.prajwalch.torrentsearch.R
import com.prajwalch.torrentsearch.ui.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { SettingsScreenTopBar(onNavigateBack = onNavigateBack) }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item { AppearanceSettings(viewModel = viewModel) }
            item { SearchSettings(viewModel = viewModel) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreenTopBar(onNavigateBack: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        title = { Text(stringResource(R.string.settings_screen_title)) },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = stringResource(R.string.button_go_back_to_search_screen)
                )
            }
        }
    )
}