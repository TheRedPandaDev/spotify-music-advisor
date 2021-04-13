# Spotify Music Advisor
A simple app that interacts with the Spotify API

## Program arguments

### -access
Specify the account access point
 
(Default: `https://accounts.spotify.com`)

### -resource
Specify the API access point
 
(Default: `https://api.spotify.com`)

### -page
Specify the amount of entries per page
 
(Default: `5`)


## Usage

### auth
Authorize with your Spotify account

### new
Get a list of new album releases featured in Spotify (shown, for example, on a Spotify player’s “Browse” tab).

(Reference: https://developer.spotify.com/documentation/web-api/reference/#endpoint-get-new-releases)

### featured
Get a list of Spotify featured playlists (shown, for example, on a Spotify player’s ‘Browse’ tab).

(Reference: https://developer.spotify.com/documentation/web-api/reference/#endpoint-get-featured-playlists)

### categories
Get a list of categories used to tag items in Spotify (on, for example, the Spotify player’s “Browse” tab).

(Reference: https://developer.spotify.com/documentation/web-api/reference/#endpoint-get-categories)

### playlists {Category_Name}
Get a list of Spotify playlists tagged with a particular category name.

The API expects category ID, so the app has to retrieve an ID using the specified name first.

(Reference: https://developer.spotify.com/documentation/web-api/reference/#endpoint-get-a-categories-playlists)
