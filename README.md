# news-app
==>Directory path for Code files:  news-app/app/src/main/java/com/example/retrofitfornewsapi/  

=>Used RETROFIT library to make REST API calls to an Open Source NEWS API to get the news feed by 
reverse Geocoding the location provided by the user by Making a Long click on the map.

=>long click on the map, google-maps-api calls onMapLongClick method which passes the Latitude and Longitude arguments to a method which
REVERSE-Geocode and returns back the Address Object.

=>Using the Address Object we calculate the Country-Code of the location and passed down this information in the INTENT object which takes to the next activity.

=>Retrofit client uses the country-code to fetch the data of that particualar country and then With the help of Recycler-View , We display the fetched result to the user.
