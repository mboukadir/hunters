package com.mb.hunters.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ReportProblem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mb.hunters.R

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    message: String = stringResource(id = R.string.generic_error_msg)
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            imageVector = Icons.Outlined.ReportProblem,
            contentDescription = "",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error)
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = message,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
    }
}
