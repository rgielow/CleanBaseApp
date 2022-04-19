package com.gielow.cleanbaseapp.feature.cryptoprices

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.gielow.cleanbaseapp.R
import com.gielow.cleanbaseapp.commons.extentions.roundTwoDigits
import com.gielow.cleanbaseapp.commons.extentions.toBrCurrency
import com.gielow.cleanbaseapp.commons.size.Size
import com.gielow.cleanbaseapp.commons.size.Weight1
import com.gielow.cleanbaseapp.domain.model.CryptoPrices
import com.gielow.cleanbaseapp.feature.MainActivity
import com.gielow.cleanbaseapp.feature.cryptoprices.CryptoPricesViewModel.ScreenState
import com.gielow.cleanbaseapp.feature.cryptoprices.CryptoPricesViewModel.UiState
import com.gielow.cleanbaseapp.feature.cryptoprices.CryptoPricesViewModel.ScreenEvent
import com.gielow.cleanbaseapp.ui.LoadingScreen

@Composable
fun CryptoPricesScreen(
    viewModel: CryptoPricesViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as MainActivity
    Screen(uiState = viewModel.uiState, onCloseClicked = viewModel::onCloseClicked)
    EventConsumer(activity = activity, viewModel = viewModel)
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: CryptoPricesViewModel
) {
    LaunchedEffect(key1 = viewModel) {
        viewModel.eventsFlow.collect { event ->
            when (event) {
                is ScreenEvent.Finish -> activity.finish()
            }
        }
    }
}


@Composable
private fun Screen(
    uiState: UiState,
    onCloseClicked: () -> Unit
) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
        ) {
            when (val screenState = uiState.screenState.collectAsState().value) {
                is ScreenState.ScreenLoading -> LoadingScreen()
                is ScreenState.ScreenError -> {}
                is ScreenState.ScreenSuccess -> ScreenContent(
                    uiState = uiState,
                    onCloseClicked = onCloseClicked
                )
            }
        }
    }
}

@Composable
private fun ScreenContent(
    uiState: UiState,
    onCloseClicked: () -> Unit
) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Size.Size16),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CloseButton(onCloseClicked = onCloseClicked)
            Spacer(modifier = Modifier.size(Size.Size24))
            Image(
                modifier = Modifier.size(width = Size.Size100, height = Size.Size100),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_btc),
                contentDescription = stringResource(id = R.string.btc_content_description)
            )

        }
        Spacer(modifier = Modifier.size(Size.Size36))
        Column(
            modifier = Modifier.padding(Size.Size16),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(Size.Size16)
        ) {
            Spacer(modifier = Modifier.size(Size.Size16))
            RefreshTimer(uiState = uiState)
            LabelWithValue(
                label = stringResource(id = R.string.crypto_buy),
                value = uiState.buy.collectAsState().value.toBrCurrency()
            )
            LabelWithValue(
                label = stringResource(id = R.string.crypto_sell),
                value = uiState.sell.collectAsState().value.toBrCurrency()
            )
            LabelWithValue(
                label = stringResource(id = R.string.crypto_open),
                value = uiState.opening.collectAsState().value.toBrCurrency()
            )
            LabelWithValue(
                label = stringResource(id = R.string.crypto_last),
                value = uiState.last.collectAsState().value.toBrCurrency()
            )
            LabelWithValue(
                label = stringResource(id = R.string.crypto_high),
                value = uiState.high.collectAsState().value.toBrCurrency()
            )
            LabelWithValue(
                label = stringResource(id = R.string.crypto_low),
                value = uiState.low.collectAsState().value.toBrCurrency()
            )
            LabelWithValue(
                label = stringResource(id = R.string.crypto_vol),
                value = uiState.vol.collectAsState().value.roundTwoDigits()
            )
            LabelWithValue(
                label = stringResource(id = R.string.crypto_date),
                value = uiState.date.collectAsState().value
            )
            Spacer(modifier = Modifier.weight(Weight1))
            Spacer(modifier = Modifier.size(Size.Size16))
        }
    }
}

@Composable
private fun CloseButton(
    onCloseClicked: () -> Unit
) {
    Row {
        Spacer(modifier = Modifier.weight(Weight1))
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_close),
            contentDescription = stringResource(id = R.string.close_icon_description),
            tint = colorResource(id = R.color.primary_500),
            modifier = Modifier
                .size(Size.Size36)
                .clickable(onClick = onCloseClicked)
        )
    }
}

@Composable
private fun LabelWithValue(
    label: String,
    value: String
) {
    Row {
        Text(
            color = colorResource(id = R.color.support_500),
            text = label,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(Weight1))
        Text(
            color = colorResource(id = R.color.support_200),
            fontFamily = FontFamily.Default,
            text = value
        )
    }
}

@Composable
private fun RefreshTimer(uiState: UiState) {
    val progress by uiState.progress.collectAsState()
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value
    LinearProgressIndicator(
        progress = animatedProgress,
        color = colorResource(id = R.color.primary_500),
        modifier = Modifier.fillMaxWidth()
    )
}


@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    Screen(uiState = UiState().apply {
        screenState.value = ScreenState.ScreenSuccess(CryptoPrices.mock())
    },
        onCloseClicked = {})
}