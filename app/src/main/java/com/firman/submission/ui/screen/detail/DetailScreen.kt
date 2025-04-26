package com.firman.submission.ui.screen.detail

import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.firman.submission.R
import com.firman.submission.di.Injection
import com.firman.submission.model.GenshinCharacter
import com.firman.submission.ui.ViewModelFactory
import com.firman.submission.ui.common.UiState
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun DetailScreen(
    characterId: String,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState(initial = UiState.Loading)
    val systemUiController = rememberSystemUiController()
    val primaryColor = MaterialTheme.colorScheme.primary

    SideEffect {
        systemUiController.setStatusBarColor(
            color = primaryColor,
            darkIcons = false
        )
    }

    LaunchedEffect(characterId) {
        viewModel.getCharacterById(characterId.toLong())
    }

    when (uiState) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            DetailContent(
                character = (uiState as UiState.Success<GenshinCharacter>).data,
                onBackClick = onBackClick
            )
        }

        is UiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(text = "Character not found")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    character: GenshinCharacter,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = character.name,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = character.image),
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = character.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Element: ${character.element}",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = character.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    DetailContent(
        character = GenshinCharacter(
            id = 1,
            image = R.drawable.ayaka, // Menggunakan resource ID (Int) langsung
            name = "Kamisato Ayaka",
            element = "Cryo",
            description = "Kamisato Ayaka, dikenal sebagai 'Shirasagi Himegimi', adalah pewaris keluarga Kamisato di Inazuma. Ia dikenal karena sikapnya yang anggun, sopan, serta kemampuan bertarung luar biasa menggunakan Cryo."
        ),
        onBackClick = {}
    )
}
