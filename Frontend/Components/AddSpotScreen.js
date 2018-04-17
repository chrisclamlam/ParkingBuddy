import React from 'react';
import { StyleSheet, Text, View, TextInput, Alert, Picker } from 'react-native'
import { MapView } from 'expo';
import { FormLabel, FormInput, Button } from 'react-native-elements'

export default class AddSpotScreen extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "",
            location: "",
            type: "",
            price: ""
        }
    }

    // Send user data to database to be authenticated
    // If user data is valid, the database will create user
    // Else, the user will be told that sign up was unsuccessful

    verifyInput = async() => {
        // Prepare user input to send to servlet
        const paramInput = 'name=' + this.state.name +
            '&location=' + this.state.location +
            '&price=' + this.state.price +
            '&spotType=' + this.state.type;

            console.log("spotType quals " + this.state.type);

        // if(this.state.name == "" ||
        //     this.state.location == "" ||
        //     this.state.type == ""){
        //     Alert.alert("Invalid Name or Location");
        //     return;
        // }
        // Fetch to our servlet: sending the user form data as the body
        // Bryce has his authen token in response header as "Set-Cookie": token

        var newLoc = this.state.location;
        for (var i = 0; i < newLoc.length; i++) {
            newLoc = newLoc.replace(" ", "+");
        }
        
        try {
            // console.log(newLoc);
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
        
        
        
        //COMMUNCIATION WITH OUR SERVEr
        
        fetch(serverIP + 'AddCustomSpot', {
            method: 'POST',
            headers: {
                'Token': global.authKey,
                'Accept': 'application/x-www-form-urlencoded',
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            timeout: 10,
            body: paramInput
        })
            .then(function (response) {
                // Handle HTTP response
                if (response.status.toString() == 200) {
                    // Save this in a global variable, locally on filesystem is slow
                    //response.headers.get('Set-Cookie'); // Gets Bryce's token
                    Alert.alert("Successful Sign Up!");
                }
                else {
                    Alert.alert("Unsuccessful Sign up: " + response.status.toString());
                }
            })
            .catch((error) => {
                //
                Alert.alert(error.message);
            });
    }

    render() {
        return (
            <View style={styles.container}>
                <Text style={styles.title} > Suggest New Location </Text>
                <View style={styles.formError}></View>



                <Text onPress={() => console.log(global.authKey)}> PRESS ME </Text>


                <FormLabel>Name of Parking Location</FormLabel>
                <FormInput onChangeText={(text) => (this.setState({ name: text }))} />
                
                <FormLabel>Enter Address of Location</FormLabel>
                <FormInput onChangeText={(text) => (this.setState({ location: text }))} />

                <FormLabel> Parking Type </FormLabel>
                <Picker selectedValue = {this.state.type} onValueChange = {this.setState.type}>
                    <Picker.Item label = "Meter" value = "1" />
                    <Picker.Item label = "Street" value = "2" />
                    <Picker.Item label = "Structure/Lot" value = "3" />
                </Picker>

                <FormLabel>Price</FormLabel>
                <FormInput onChangeText={(text) => (this.setState({ price: text }))} />

                <View style={{ height: 80 }} />
                <Button
                    buttonStyle={{ borderRadius: 10, backgroundColor: '#f8971d', width: '100%' }}
                    onPress={() => this.verifyInput(this)} title="Submit Location Suggestion" />

                <Button
                    buttonStyle={{ borderRadius: 10, backgroundColor: 'transparent' }}
                    textStyle= {{color: 'gray'}}
                    fontSize={15}
                    color='black'
                    onPress={() => this.props.navigation.pop()} title="Back to Search" />
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
