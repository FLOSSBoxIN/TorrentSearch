package com.prajwalch.torrentsearch.ui.screens.settings

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource

import com.prajwalch.torrentsearch.R
import com.prajwalch.torrentsearch.data.DarkTheme
import com.prajwalch.torrentsearch.ui.components.SettingsItem
import com.prajwalch.torrentsearch.ui.components.SettingsOptionMenu
import com.prajwalch.torrentsearch.ui.components.SettingsSectionTitle
import com.prajwalch.torrentsearch.ui.viewmodel.SettingsViewModel


data class SettingsOptionMenuEvent(
    @param:StringRes
    val title: Int,
    val options: List<String>,
    val selectedOption: Int,
    val onOptionSelect: (Int) -> Unit,
)

@Composable
fun AppearanceSettings(viewModel: SettingsViewModel) {
    val settings by viewModel.appearanceSettings.collectAsState()
    var optionMenuEvent by remember(settings) { mutableStateOf<SettingsOptionMenuEvent?>(null) }

    optionMenuEvent?.let { event ->
        SettingsOptionMenu(
            title = event.title,
            options = event.options,
            selectedOption = event.selectedOption,
            onOptionSelect = event.onOptionSelect,
            onDismissRequest = { optionMenuEvent = null },
        )
    }

    SettingsSectionTitle(title = stringResource(R.string.settings_section_appearance))
    SettingsItem(
        leadingIconId = R.drawable.ic_palette,
        headline = stringResource(R.string.setting_enable_dynamic_theme),
        onClick = {
            viewModel.updateEnableDynamicTheme(!settings.enableDynamicTheme)
        },
        trailingContent = {
            Switch(
                checked = settings.enableDynamicTheme,
                onCheckedChange = { viewModel.updateEnableDynamicTheme(it) },
            )
        },
    )
    SettingsItem(
        leadingIconId = R.drawable.ic_dark_mode,
        headline = stringResource(R.string.setting_dark_theme),
        onClick = {
            optionMenuEvent = SettingsOptionMenuEvent(
                title = R.string.setting_dark_theme,
                options = DarkTheme.entries.map { it.toString() },
                selectedOption = settings.darkTheme.ordinal,
                onOptionSelect = { viewModel.updateDarkTheme(DarkTheme.fromInt(it)) },
            )
        },
        supportingContent = settings.darkTheme.toString(),
    )

    val showPureBackSetting = when (settings.darkTheme) {
        DarkTheme.On -> true
        DarkTheme.Off -> false
        DarkTheme.FollowSystem -> isSystemInDarkTheme()
    }

    AnimatedVisibility(
        visible = showPureBackSetting,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        SettingsItem(
            leadingIconId = R.drawable.ic_contrast,
            headline = stringResource(R.string.setting_pure_black),
            onClick = {
                viewModel.updatePureBlack(!settings.pureBlack)
            },
            trailingContent = {
                Switch(
                    checked = settings.pureBlack,
                    onCheckedChange = { viewModel.updatePureBlack(it) },
                )
            },
        )
    }
}