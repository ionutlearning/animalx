package com.example.animalx.presentation.ui.screen.home

import ErrorMessage
import Loading
import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animalx.domain.ViewState
import com.example.animalx.domain.entity.Animal
import com.example.animalx.presentation.ui.theme.AnimalXTheme


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onItemClickListener: (Long) -> Unit
) {
    val viewState by viewModel.animalsStateFlow.collectAsState()
    val pullToRefreshState = rememberPullRefreshState(
        refreshing = viewState == ViewState.Loading,
        onRefresh = { viewModel.getAnimals() },
    )

    Scaffold(
        content = { _ ->
            Box(modifier = Modifier.pullRefresh(pullToRefreshState),
                contentAlignment = Alignment.Center) {
                HomeContent(
                    viewState,
                    onItemClickListener
                )
                PullRefreshIndicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    refreshing = viewState == ViewState.Loading,
                    state = pullToRefreshState
                )
            }
        }
    )
}

@Composable
fun HomeContent(
    animalsViewState: ViewState<List<Animal>>,
    onItemClickListener: (Long) -> Unit
) {
    when (animalsViewState) {
        is ViewState.Success -> {
            AnimalList(
                animals = animalsViewState.data,
                onItemClickListener = onItemClickListener
            )
        }

        is ViewState.Error -> {
            ErrorMessage(animalsViewState.message)
        }

        is ViewState.Loading -> {
            Loading()
        }

        else -> Unit
    }
}

@Composable
private fun AnimalList(
    animals: List<Animal>,
    onItemClickListener: (Long) -> Unit
) {
    LazyColumn(
        state = rememberLazyListState()
    ) {
        items(items = animals) { animal ->
            AnimalItem(
                animal = animal,
                onItemClickListener = onItemClickListener
            )
        }
    }
}

@Composable
private fun AnimalItem(
    animal: Animal,
    onItemClickListener: (Long) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(
            5.dp
        )
    ) {
        ItemCardContent(
            animal,
            onItemClickListener
        )
    }
}

@Composable
private fun ItemCardContent(
    animal: Animal,
    onItemClickListener: (Long) -> Unit
) {
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxSize()
        .clickable {
            onItemClickListener(animal.id)
        }) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
            text = animal.name,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DarkPreview"
)
@Composable
fun HomeScreenDarkPreview() {
    HomeScreenPreview()
}

@Preview
@Composable
fun HomeScreenPreview() {
    AnimalXTheme {
        HomeContent(
            previewItem,
            onItemClickListener = {})
    }
}

private val previewItem = ViewState.Success(
    listOf(
        Animal(
            1L,
            "Max"
        )
    )
)