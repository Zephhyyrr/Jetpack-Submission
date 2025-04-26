package com.firman.submission.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.firman.submission.R
import com.firman.submission.di.Injection
import com.firman.submission.model.GenshinCharacter
import com.firman.submission.ui.ViewModelFactory
import com.firman.submission.ui.common.UiState
import com.firman.submission.ui.components.ListCharacterItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState(initial = UiState.Loading).value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.menu_home),
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllCharacters()
            }

            is UiState.Success -> {
                HomeContent(
                    genshinCharacters = uiState.data,
                    modifier = Modifier.padding(innerPadding),
                    navigateToDetail = navigateToDetail,
                )
            }

            is UiState.Error -> {
                // TODO: tampilkan pesan error kalau perlu
            }
        }
    }
}

@Composable
fun HomeContent(
    genshinCharacters: List<GenshinCharacter>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(genshinCharacters, key = { it.id }) { character ->
            ListCharacterItem(
                image = character.image,
                name = character.name,
                element = character.element,
                description = character.description,
                modifier = Modifier.clickable {
                    navigateToDetail(character.id)
                }
            )
        }
    }
}
