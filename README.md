# SimpleApp

SimpleApp is an Android application built with Jetpack Compose. It loads JSON data from assets, displays a list of news items, and navigates to a detail screen upon selection.

## Features
- Loads and parses JSON data from `assets/news.json`.
- Displays a list of news articles using `LazyColumn`.
- Uses `NavHostController` for navigation between screens.
- Implements a shared `ViewModel` to pass selected item data.
- Loads and displays images using the `Coil` library.

## Screens
### Main Screen
- Displays a list of news articles with images.
- Clicking on an article navigates to the Detail Screen.

### Detail Screen
- Shows the full details of the selected news article.
- Displays the article image, title, content, authors, and more.
- Includes a "Back" button to return to the Main Screen.

## How to Run
1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/SimpleApp.git
   ```
2. Open the project in **Android Studio**.

