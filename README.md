# Geo-news-app
==>Directory path for Code files:  news-app/app/src/main/java/com/example/retrofitfornewsapi/  

=>Used RETROFIT library to make REST API calls to an Open Source NEWS API to get the news feed by 
reverse Geocoding the location provided by the user by Making a Long click on the map.

=>long click on the map, google-maps-api calls onMapLongClick method which passes the Latitude and Longitude arguments to a method which
REVERSE-Geocode and returns back the Address Object.

=>Using the Address Object we calculate the Country-Code of the location and passed down this information in the INTENT object which takes us to the next activity.

=>Retrofit client uses the country-code to fetch the data of that particualar country and then With the help of Recycler-View , We display the fetched result to the user.

## Some Screenshots: 

<img src="https://user-images.githubusercontent.com/69712875/95579960-23dabb00-0a54-11eb-8d19-1d6e5217adf6.jpg" width="200" height="390">

<img src="https://user-images.githubusercontent.com/69712875/95579967-263d1500-0a54-11eb-80ca-017fa93ae1ea.jpg" width="200" height="390">

<img src="https://user-images.githubusercontent.com/69712875/95579968-26d5ab80-0a54-11eb-8bda-6abea9053056.jpg" width="200" height="390">

<img src="https://user-images.githubusercontent.com/69712875/95579970-2806d880-0a54-11eb-9c1b-1b1f81ee294f.jpg" width="200" height="390">

<img src="https://user-images.githubusercontent.com/69712875/95579972-29380580-0a54-11eb-9bc1-06308a9b789d.jpg" width="200" height="390">
