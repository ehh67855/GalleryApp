# Gallery App
GalleryApp is a JavaFX application that showcases a dynamic and interactive gallery of images sourced from iTunes. It is developed in Java and utilizes the JavaFX framework for its graphical user interface. The application allows users to search for images related to a specific query (e.g., a music artist), and displays a selection of relevant images in a tile format. The gallery's content can be dynamically updated and altered through user interactions.

## Features
- **Dynamic Image Gallery:** Displays images in a grid layout, sourced based on user search queries.
- **Interactive User Interface:** Includes buttons for updating the gallery, pausing/resuming image swapping, and text fields for inputting search queries.
- **Automatic Image Swapping:** Features an automatic swapping mechanism that periodically updates the displayed images.
- **Customizable Search:** Users can input their own search terms to customize the content displayed in the gallery.
- **Error Handling and Alerts:** Provides alerts for various scenarios such as insufficient search results.
- **About Section:** Includes an 'About' section with information about the application and its creator.

## Usage
- **Search Functionality:** Enter a search term in the provided text field and click 'Update' to load images related to the query.
- **Play/Pause Swapping:** Use the 'Play' button to start the automatic swapping of images and 'Pause' to stop it.
- **Exit and Help Options:** Accessible through the menu bar, allowing users to exit the application or seek help.

## Technical Details
- **JavaFX Application:** Utilizes JavaFX for UI components including menus, buttons, text fields, and image views.
- **iTunes API Integration:** Fetches image data from the iTunes API based on user queries.
- **Multithreading:** Implements threading for handling network requests and UI updates.
  
## System Requirements
- Java Runtime Environment (JRE) and JavaFX.
- Internet connection for fetching images from iTunes.

## Installation
- Clone or download the repository.
- Ensure Java and JavaFX are installed on your system.
- Compile the source files.
- Run GalleryDriver to start the application.
