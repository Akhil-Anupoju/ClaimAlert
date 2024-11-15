# Claim Alert
App that tracks items purchased according to expiry date, and reminds the user. Plus, many other useful grocery/inventory management features

## About

Claim Alert is an app created to help you keep track of expiry dates. 
Get reminded by notifications once something is expiring within 14 days.

This application has been created by AKhil Anupoju, and has been released under the MIT license.

## Features

This app lets you:
- Add items with expiry dates
- Choose category while adding items for greater organization
- Attach Images to items, making it easy to remember
- View the list of items sorted by Item Name or by Expiration Date
- Choose between MM-DD-YYYY and DD-MM-YYYY formats
- Add/Remove custom categories
- Easily Disable/Enable notifications

## Technicalities

- This application uses a local SQLite database with multiple tables to store everything
- Images are stored in the app's cached directory, ensuring that no one except the app can access the images
- The targeted API Level is 33 (Android 13), and the minimum API Level is 26 (Android 8.0)
- Java is the only language used in this application
- I have not used any external Third-party libraries except androidx & Material
