import React from 'react';
import { StyleSheet, Text, View, TextInput, Alert } from 'react-native'
import { MapView } from 'expo';
import { FormLabel, FormInput, Button } from 'react-native-elements'

export default class SearchLocationScreen extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            location: '',
        }
    }

    // Send user data to database to be authenticated
    // If user data is valid, the database will create user
    // Else, the user will be told that sign up was unsuccessful
    // google api key AIzaSyBcRR3ARN-8LLMfbjt5dfMgZ6ERKkKEdxA for turning search terms into
    // long/lat

    // URL to call google api
    // https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=YOUR_API_KEY

    verifyInput =  async () => {
        // Prepare user input to send to servlet
        const paramInput = '&location=' + this.state.location;
        var lat;
        var lng;

        // Check to see if user entered an address or keyword
        if(this.state.location == ""){
            Alert.alert("Invalid Location");
        }

        var newLoc = this.state.location;
        for(var i = 0; i < newLoc.length; i++){
            newLoc = newLoc.replace(" ", "+");
        }

        // Call google api to get coordinates
        try {
            let response = await fetch('https://maps.googleapis.com/maps/api/geocode/json?address=' + newLoc + '&key=' + 'AIzaSyBcRR3ARN-8LLMfbjt5dfMgZ6ERKkKEdxA');
            let responseJson = await response.json();
            if (responseJson.status == ('OK')) {
                lat = responseJson.results[0].geometry.location.lat;
                lng = responseJson.results[0].geometry.location.lng;
            }
            else {
                Alert.alert("Unable to search. Please try again.");
                return;
            }
        } catch(error){
            console.error(error);
        }

        // Servlet request param
        var coords = "lat=" + lat + "&lng=" + lng;

        // Now that we have long/lat send a request to our servlet
        try {
            let response = await fetch(global.serverIP + '/SearchLocation', {
                method: 'POST',
                headers: {
                    'Accept': 'application/x-www-form-urlencoded',
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                timeout: 10,
                body: coords
            });
            let responseJson = await response.json();
            if (responseJson.status == 200) { // Request is good and there are results
                this.props.navigation.push({
                    name: 'MapScreen',
                    passProps: {
                        markers: responseJson.responseText
                    }
                });
            }
            else { // No results
                Alert.alert("Unable to find any location");
                this.props.navigation.push({
                    name: 'MapScreen',
                    passProps: {
                        markers: ""
                    }
                });
            }
        } catch(error){
            console.error(error);
        }
        //this.props.navigation.push('MapScreen');
    }

    render() {
        return (
            <View style={styles.container}>
                <Text style={styles.title} > Location to Search for Spots</Text>

                <FormLabel>Location</FormLabel>
                <FormInput onChangeText={(text) => (this.setState({ location: text }))} />

                <View style={{ height: 80 }} />
                <Button
                    buttonStyle={{ borderRadius: 10, backgroundColor: '#f8971d', width: '100%' }}
                    onPress={() => this.verifyInput(this)} title="Search" />

                <Button
                    buttonStyle={{ borderRadius: 10, backgroundColor: 'transparent' }}
                    textStyle= {{color: 'gray'}}
                    fontSize={15}
                    color='black'
                    onPress={() => this.props.navigation.pop()} title="Back to Login" />
            </View >
        );
    }
}



const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        justifyContent: 'center',
    },
    title: {
        textAlign: 'center',
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
