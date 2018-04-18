import React from 'react';
import { StyleSheet, Text, View, TextInput, Image, Alert, ScrollView, } from 'react-native';
import { MapView } from 'expo';
import { FormLabel, FormInput, Button, List, ListItem,  } from 'react-native-elements'
import { StackNavigator } from 'react-navigation'


export default class ProfileScreen extends React.Component {
    constructor(props) {
        super(props)

        // Variables for current page
        this.state = {
            currLocation: {},
            markerLocation: {},
        }
    }

    // Get price comparison between lyft and uber
    getPriceCompare = async () => {

    }

    // Get directions for marker
    // API key for Google Maps Directions: AIzaSyA4bras52B_hfyipj6aZ0_lwLhB5fp4I5E
    // getDirections = async () => {
    //     //API call: https://maps.googleapis.com/maps/api/directions/json?origin=lat,lng&destination=lat,lng&key=AIzaSyA4bras52B_hfyipj6aZ0_lwLhB5fp4I5E
    //     try {
    //         let response = await fetch('https://maps.googleapis.com/maps/api/directions/json?origin='
    //             this.currLocation.latitude + ',' +
    //             this.currLocation.longitude +
    //             '&destination=' +
    //             this.markerLocation.latitude + ',' +
    //             this.markerLocation.longitude + &key=AIzaSyA4bras52B_hfyipj6aZ0_lwLhB5fp4I5E');
    //         let responseJson = await response.json();
    //         if (responseJson.status == 'OK') {
    //             lat = responseJson.results[0].geometry.location.lat;
    //             lng = responseJson.results[0].geometry.location.lng;
    //         }
    //         else {
    //             Alert.alert("Unable to search. Please try again. Response status: " + response.status);
    //
    //             return;
    //         }
    //     } catch(error){
    //        console.error(error);
    //     }
    // }

    // To set up props when page loads
    componentWillMount = () => {

        const {params} = this.props.navigation.state;

        // See if params are being sent
        console.log("params: " + params.markerCoord);
        // console.out("MarkerLocation: " + params.markerCoord);

        // Set state variables to params that were sent
        this.setState({
            currLocation: params.initRegion,
            markerLocation: params.markerCoord,
        });
        console.log("markerLoc: " + markerLocation);
    }


        render() {
        return (
            <View style={styles.container}>
                <Text onPress= {() => this.props.navigation.pop()}> back </Text>

                <Text> Details Screen </Text>

                { /* Scrollview for details */ }
                <ScrollView>
                    <View>
                        { /* View for General Information
                             - Average rating of location
                             - Comments for the location
                             - Available or historic price for location
                             - Option to start GPS towards location
                        */}
                        <FormLabel>Name:</FormLabel>
                        <View></View>
                        <FormLabel>Average Rating</FormLabel>
                        <View></View>
                        <FormLabel>Historic Price</FormLabel>
                        <View></View>
                        <FormLabel>GPS</FormLabel>

                    </View>

                    <View >
                        { /* Options for logged in users
                            - Add parking structure to favorites list
                            - Compare cost of driving to cost of ride sharing services
                            - Rate or comment on location
                            - Report current price of the parking structure
                         */}
                    </View>

                    <View>
                        { /* View for Comments on location */}
                    </View>

                </ScrollView>
            </View >
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: 'white',
        // alignItems: 'center',
        justifyContent: 'center',
    },
});
