package tawuniya.challenge.businessCards.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerLoadingScreen() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        repeat(5) { // Simulate multiple items
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(vertical = 8.dp)
            )
        }
    }
}
