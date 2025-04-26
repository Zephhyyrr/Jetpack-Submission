package com.firman.submission.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.firman.submission.R
import com.firman.submission.ui.theme.SubmissionTheme

@Composable
fun ListCharacterItem(
    image: Int,
    name: String,
    element: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = image),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    color = MaterialTheme.colorScheme.primary,
                    text = element,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListCharacterItemPreview() {
    SubmissionTheme {
        ListCharacterItem(
            image = R.drawable.ayaka,
            name = "Kamisato Ayaka",
            element = "Cryo",
            description = "Kamisato Ayaka, dikenal sebagai 'Shirasagi Himegimi', adalah pewaris keluarga Kamisato di Inazuma. Ia dikenal karena sikapnya yang anggun, sopan, serta kemampuan bertarung luar biasa menggunakan Cryo."
        )
    }
}
