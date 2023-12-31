package com.example.animalx.presentation.ui.screen.detail

import ErrorMessage
import Loading
import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animalx.domain.ViewState
import com.example.animalx.domain.entity.AnimalDetail
import com.example.animalx.presentation.ui.theme.AnimalXTheme
import contentPaddingModifier


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    id: Long,
    viewModel: DetailViewModel,
    backNavigation: () -> Unit
) {
    LaunchedEffect(
        key1 = true,
        block = {
            viewModel.getAnimalDetails(id)
        })

    val viewState by viewModel.animalStateFlow.collectAsState()

    Scaffold(topBar = {
        TopBar(backNavigation = backNavigation)
    },
        content = { _ ->
            DetailContent(viewState)
        })
}

@Composable
fun DetailContent(viewState: ViewState<AnimalDetail>) {
    when (viewState) {
        is ViewState.Success -> {
            AnimalDetail(
                animal = viewState.data
            )
        }
        is ViewState.Error -> {
            ErrorMessage(viewState.message)
        }
        is ViewState.Loading -> {
            Loading()
        }
        else -> Unit
    }
}

@Composable
private fun AnimalDetail(animal: AnimalDetail) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = contentPaddingModifier
    ) {
        AnimalDetailCardContent(animal)
    }
}

@Composable
private fun AnimalDetailCardContent(animal: AnimalDetail) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(
            text = animal.name,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
        Text(
            text = "Breed: ${animal.breed}",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Size: ${animal.size}",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Gender: ${animal.gender}",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Status: ${animal.status}",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Distance: ${animal.distance}",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    backNavigation: () -> Unit
) {
    TopAppBar(navigationIcon = {
        BackAction(onClick = backNavigation)
    },
        title = {
            Text(
                text = "Animal Details",
            )
        })
}

@Composable
fun BackAction(
    onClick: () -> Unit
) {
    IconButton(onClick = { onClick() }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back Arrow",
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Composable
fun DetailScreenDarkPreview() {
    DetailScreenPreview()
}

@Preview
@Composable
fun DetailScreenPreview() {
    AnimalXTheme {
        DetailContent(
            previewItem
        )
    }
}

private val previewItem = ViewState.Success(
    AnimalDetail(
        "Rex",
        "lup",
        "large",
        "M",
        "Single",
        123.0
    )
)