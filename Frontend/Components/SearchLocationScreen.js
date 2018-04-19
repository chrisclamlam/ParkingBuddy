import React from 'react';
import { StyleSheet, Text, View, TextInput, Alert } from 'react-native'
import { MapView } from 'expo';
import { FormLabel, FormInput, Button } from 'react-native-elements'
import { Ionicons } from '@expo/vector-icons';
import { StackNavigator } from 'react-navigation'


export default class SearchLocationScreen extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            location: '',
            json: '',
            foundLocation: [],
        }
    }

    // Send user data to database to be authenticated
    // If user data is valid, the database will create user
    // Else, the user will be told that sign up was unsuccessful
    // google api key AIzaSyBcRR3ARN-8LLMfbjt5dfMgZ6ERKkKEdxA for turning search terms into
    // long/lat

    // URL to call google api
    // https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=YOUR_API_KEY

    toProperJson = (params) => {
        // console.log("params: " + params);
        var jsonFormat = [];
        var json = params;
        // console.log("JSON array object: " + json);
        for(var i = 0; i < json.length; i++){
            // console.log("Array index " + i + " keys: " + Object.keys(json[i]));
            var spot = {
                id: json[i].id,
                remoteId: json[i].remoteId,
                spotType: json[i].spotType,
                label: json[i].label,
                coordinate: {
                    latitude: json[i].latitude,
                    longitude: json[i].longitude
                }
            };
            // console.log("Keys for new spot: " + Object.keys(spot));
            jsonFormat.push(spot);
        }
        // console.log("jsonFormat: ");

        return jsonFormat;
    }



    verifyInput = async () => {
        // Prepare user input to send to servlet
        const paramInput = '&location=' + this.state.location;
        var lat ;
        var lng ;
        var responseJson = "";

        // For testing only

        // this.props.navigation.navigate('MapScreen', {
        //     markers: [
        //         {
        //             coordinate: {
        //                 latitude: 11,
        //                 longitude: 12,
        //             },
        //             title: "test1",
        //             description: "test1",
        //             distance: 12,
        //             spotType: 2
        //         },
        //     ],
        // });

        var newLoc = this.state.location;
        for (var i = 0; i < newLoc.length; i++) {
            newLoc = newLoc.replace(" ", "+");
        }


        // Call google api to get coordinates
        try {
            let response = await fetch('https://maps.googleapis.com/maps/api/geocode/json?address=' + newLoc + '&key=' + 'AIzaSyBcRR3ARN-8LLMfbjt5dfMgZ6ERKkKEdxA');
            let responseJson = await response.json();
            if (responseJson.status == 'OK') {
                lat = responseJson.results[0].geometry.location.lat;
                lng = responseJson.results[0].geometry.location.lng;
            }
            else {
                Alert.alert("Unable to search. Please try again. Response status: " + response.status);

                return;
            }
        } catch(error){
            console.error(error);
        }

        // Servlet request param
        var params = "lat=" + lat + "&lng=" + lng + '&keyword=' + newLoc;
        console.log("Params: " + params);
        // // Now that we have long/lat send a request to our servlet
        try {
            let response = await fetch(global.serverIP + 'SearchLocation?' + params, {
                method: 'GET',
                headers: {
                    'Accept': 'application/x-www-form-urlencoded',
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                timeout: 10,
                // body: coords
            });
            let responseJson = await response.json();
            // console.log(response);
            if (response.status == 200) { // Request is good and there are results
                var json = this.toProperJson(responseJson);

                // Set the foundLocation to the location that we got
                // console.log("foundLocation: " + Object.keys(json[0]));

                this.props.navigation.navigate('MapScreen', {
                    markers: json
                });
            }
            else { // No results
                Alert.alert("Unable to find any location");

            }
        } catch(error){
            console.error(error);
        }
        return;
    }

    render() {
        return (
            <View style={styles.container}>

                <Ionicons
                    style={{ marginLeft: '90%',marginTop:'10%' }}
                    onPress={() => { this.props.navigation.push('ProfileScreen') }}
                    name="md-person" size={32} color="gray" />


                <View style={{ marginTop:'50%'}}>
                    <Text style={styles.title} > Search </Text>

                    <FormLabel>Location</FormLabel>
                    <FormInput onChangeText={(text) => (this.setState({ location: text }))} />

                    <View style={{ height: 80 }} />
                    <Button
                        buttonStyle={{ borderRadius: 10, backgroundColor: '#f8971d', width: '100%' }}
                        onPress={() => this.verifyInput(this)} title="Search" />

                    {/* // <Button */}
                    {/* //     buttonStyle={{ borderRadius: 10, backgroundColor: 'transparent' }} */}
                    {/* //     textStyle={{ color: 'gray' }} */}
                    {/* //     fontSize={15} */}
                    {/* //     color='black' */}
                    {/* //     onPress={() => this.props.navigation.pop()} title="Back to Login" /> */}
                </View>
            </View >
        );
    }
}



const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        // justifyContent: 'center',
    },
    title: {
        // textAlign: 'center',
        fontSize: 35,
        marginLeft: 10,
        marginBottom: 30,
        fontWeight: 'bold',
        color: '#f8971d'
    },
    textFields: {
        borderRadius: 10,
        width: '80%',
        height: 40,
        borderColor: 'gray',
        borderWidth: 1,
        margin: 5,
        padding: 10
    },
});
