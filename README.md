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

## Example

    > new
    Please, provide access for application.
    > auth
    use this link to request the access code:
    https://accounts.spotify.com/authorize?client_id=a19ee7dbfda443b2a8150c9101bfd645&redirect_uri=http://localhost:8080&response_type=code
    waiting for code...
    code received
    Making http request for access_token...
    Success!
    > new
    OT ALL HEROES WEAR CAPES
    [Metro Boomin, Travis Scott, 21 Savage]
    https://open.spotify.com/album/1zNr37qd3iZJ899byrTkcj

    I Used To Know Her - Part 2 - EP
    [H.E.R.]
    https://open.spotify.com/album/46imFLgb9fR1Io6EoPYeQh

    The Last Rocket
    [Takeoff]
    https://open.spotify.com/album/5XRCcUfwtLNQflDd9cfz4U

    Interstate Gospel
    [Pistol Annies]
    https://open.spotify.com/album/0IXxmmlfSQxgJNWnNjHhgJ

    El Mal Querer
    [ROSALÍA]
    https://open.spotify.com/album/355bjCHzRJztCzaG5Za4gq

    ---PAGE 1 OF 5---
    > prev
    No more pages.
    > next
    Mountains
    [Sia, Diplo, Labrinth]
    https://open.spotify.com/album/3dB0bCgmpEgCSr3aU1bOtv

    Pussy Is God
    [King Princess]
    https://open.spotify.com/album/4UzCY6ikiEN4rgY26I4jg0

    Shootin Shots (feat. Ty Dolla $ign & Tory Lanez)
    [Trey Songz, Ty Dolla $ign]
    https://open.spotify.com/album/6Erhbwa5HmDwuzYacUpLPr

    Runaway
    [Lil Peep]
    https://open.spotify.com/album/38sesm68q3lg21o6Lpzslc

    RESET
    [Moneybagg Yo]
    https://open.spotify.com/album/547DJFUYOl2SBYJbo2jZX1

    ---PAGE 2 OF 5---
    > categories
    Top Lists
    Mood
    Chill
    Hip-Hop
    Electronic/Dance
    ---PAGE 1 OF 10---
    > next
    Kids & Family
    Rock
    Indie
    Happy Holidays
    Workout
    ---PAGE 2 OF 10---
    > exit
