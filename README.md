# MultiScreen

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
