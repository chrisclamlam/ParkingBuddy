import React from 'react';
import { StyleSheet, Text, View, TextInput, Image, Alert, ScrollView,  FlatList,} from 'react-native';
import { MapView } from 'expo';
import { FormLabel, FormInput, Button, List, ListItem,  } from 'react-native-elements';
export default class ProfileScreen extends React.Component {
    constructor(props) {
        super(props)

        // Variables for current page
        this.state = {
            currLocation: {},
            markerLocation: {},
            lyftUberComp: {},
            spots: [],
            isLoad: false,
        }
    }
    /*
    Parking spots json:
     [{"id":1283,"remoteId":"3df956c8d01360c3aac0359146b8a230a6462076","spotType":3,"label":"Parking Lot M","latitude":-118.284729,"longitude":34.023689},{"id":1256,"remoteId":"3df956c8d01360c3aac0359146b8a230a6462076","spotType":3,"label":"Parking Lot M","latitude":-118.284729,"longitude":34.023689},{"id":1266,"remoteId":"3df956c8d01360c3aac0359146b8a230a6462076","spotType":3,"label":"Parking Lot M","latitude":-118.284729,"longitude":34.023689},{"id":1296,"remoteId":"3df956c8d01360c3aac0359146b8a230a6462076","spotType":3,"label":"Parking Lot M","latitude":-118.284729,"longitude":34.023689},{"id":1306,"remoteId":"3df956c8d01360c3aac0359146b8a230a6462076","spotType":3,"label":"Parking Lot M","latitude":-118.284729,"longitude":34.023689},{"id":1276,"remoteId":"3df956c8d01360c3aac0359146b8a230a6462076","spotType":3,"label":"Parking Lot M","latitude":-118.284729,"longitude":34.023689},{"id":1290,"remoteId":"e97dc0ef6bc39699eb3a6cb5838d74642d03497c","spotType":3,"label":"Parking Lot 23","latitude":-118.284378,"longitude":34.023621},{"id":1300,"remoteId":"e97dc0ef6bc39699eb3a6cb5838d74642d03497c","spotType":3,"label":"Parking Lot 23","latitude":-118.284378,"longitude":34.023621},{"id":1250,"remoteId":"e97dc0ef6bc39699eb3a6cb5838d74642d03497c","spotType":3,"label":"Parking Lot 23","latitude":-118.284378,"longitude":34.023621},{"id":1260,"remoteId":"e97dc0ef6bc39699eb3a6cb5838d74642d03497c","spotType":3,"label":"Parking Lot 23","latitude":-118.284378,"longitude":34.023621},{"id":1282,"remoteId":"e97dc0ef6bc39699eb3a6cb5838d74642d03497c","spotType":3,"label":"Parking Lot 23","latitude":-118.284378,"longitude":34.023621},{"id":1270,"remoteId":"e97dc0ef6bc39699eb3a6cb5838d74642d03497c","spotType":3,"label":"Parking Lot 23","latitude":-118.284378,"longitude":34.023621},{"id":1310,"remoteId":"e97dc0ef6bc39699eb3a6cb5838d74642d03497c","spotType":3,"label":"Parking Lot 23","latitude":-118.284378,"longitude":34.023621},{"id":1247,"remoteId":"d614b85c8b2e4201f17c0313a07a48a38c5253b5","spotType":3,"label":"Parking Lot M","latitude":-118.285065,"longitude":34.023773},{"id":1257,"remoteId":"d614b85c8b2e4201f17c0313a07a48a38c5253b5","spotType":3,"label":"Parking Lot M","latitude":-118.285065,"longitude":34.023773},{"id":1285,"remoteId":"d614b85c8b2e4201f17c0313a07a48a38c5253b5","spotType":3,"label":"Parking Lot M","latitude":-118.285065,"longitude":34.023773},{"id":1307,"remoteId":"d614b85c8b2e4201f17c0313a07a48a38c5253b5","spotType":3,"label":"Parking Lot M","latitude":-118.285065,"longitude":34.023773},{"id":1267,"remoteId":"d614b85c8b2e4201f17c0313a07a48a38c5253b5","spotType":3,"label":"Parking Lot M","latitude":-118.285065,"longitude":34.023773},{"id":1277,"remoteId":"d614b85c8b2e4201f17c0313a07a48a38c5253b5","spotType":3,"label":"Parking Lot M","latitude":-118.285065,"longitude":34.023773}]

     */



    // Get actual spots to show on the map
    getSpots = async () => {
        console.log("GetSpots markerLocation: " + JSON.stringify(this.state.markerLocation));

        let params = "lat=" + this.state.markerLocation.coordinate.latitude +
            "&lng=" + this.state.markerLocation.coordinate.longitude
        console.log("params getSpot: " + params);
        var json = null;
        try {
            let response = await fetch(global.serverIP + 'SearchSpot?' + params, {
                method: 'GET',
                headers: {
                    'Accept': 'application/x-www-form-urlencoded',
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                // body: coords
            });
            /*.then((respons) => response.json())
                          .then((responseJson) => {
                            console.log(responseJson);
                            console.log("Spots: " + responseJson);
                            this.setState ({
                                spots: responseJson,
                            })
                          })
                          .catch((error) => {
                            throw error;
                          }); */
            let responseJson = await response.json();
            this.setState({
                spots: responseJson,
                isLoad: true,
            })
            console.log("ResponseJson getSpots: " + JSON.stringify(this.state.spots));
        } catch (error) {
            console.error(error);
        }
    }


    // Get price comparison between lyft and uber
    getPriceCompare = async () => {
        let params = "start_lat=" + this.state.currLocation.latitude + "&start_long=" +
            this.state.currLocation.latitude  + "&end_lat=" +
            this.state.markerLocation.latitude + "&end_long=" + this.state.markerLocation.longitude;

        console.log("state vars: " + this.state);
        try {
            let response = await fetch(global.serverIP + 'CompareLyftandUber?' + params, {
                method: 'GET',
                headers: {
                    'Accept': 'application/x-www-form-urlencoded',
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
            });
            let responseJson = await response.text();

            if (response.status == 200) { // Request is good and there are results
                var price = this.toProperJson(responseJson);

                // Pass array from get spots into MapScreen
                this.setState({
                    lyftUberComp: price, })
                Alert.alert(this.state.lyftUberComp);
            }
            else { // No results
                Alert.alert("Unable to find price comparison");

            }
        } catch(error){
            console.error(error);
        }

    }

    // Get actual spots to show on the map

    // getSpots = async () => {
    //     let params = "lat=" + this.state.foundLocation.coordinate.latitude +
    //         "&lng=" + this.state.foundLocation.coordinate.longitude
    //     console.log("params getSpot: " + params);
    //     try {
    //         let response = await fetch(global.serverIP + 'SearchSpot?' + params, {
    //             method: 'GET',
    //             headers: {
    //                 'Accept': 'application/x-www-form-urlencoded',
    //                 'Content-Type': 'application/x-www-form-urlencoded',
    //             },
    //             timeout: 10,
    //             // body: coords
    //         });
    //         let responseJson = await response.json();
    //
    //         if (response.status == 200) { // Request is good and there are results
    //             var json = this.toProperJson(responseJson);
    //             console.log("JSON getSpot: " + json);
    //
    //             // Pass array from get spots into MapScreen
    //             this.props.navigation.navigate('MapScreen', {
    //                 markers: json
    //             });
    //
    //         }
    //         else { // No results
    //             Alert.alert("Unable to find any location");
    //
    //         }
    //     } catch(error){
    //         console.error(error);
    //     }
    // }


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

    componentDidMount = () => {
        this.getSpots();
    }

    // To set up props when page loads
    componentWillMount = (props) => {
        const {params} = this.props.navigation.state;
        // See if params are being sent -> yes
        /* Format of params.markerCoord:
        {"id":-1,
        "remoteId":" ....,
        "spotType": ...,
        "label":...,
        "coordinate":{
            "latitude":...,
            "longitude":....}
        }

        Format of params.initRegion
        {"latitude":37.4219983,
         "longitude":-122.084,
         "latitudeDelta":0.092,
         "longitudeDelta":0.0221}
        */

        // Set state variables to params that were sent
        console.log("makerLocation: " + JSON.stringify(params.markerCoord));
        this.setState({
            currLocation: params.initRegion,
            markerLocation: params.markerCoord,
        });
        // this.getPriceCompare();
        // console.log("makerLocation: " + this.state.markerLocation);
    }
    componentDidMount = (props) => {
        this.getSpots();
    }

    render() {
        return (
            <View style={styles.container}>
                <Text style={styles.button} onPress= {() => this.props.navigation.pop()}> Back </Text>

                <Text style={styles.title}
                > Details Screen </Text>

                { /* Scrollview for details */ }
                <FormLabel style="color: '#f8971d',"
                >Location Name</FormLabel>
                {/*<Text>{this.markerLocation.label}</Text>*/}

                <FormLabel style="color: '#f8971d',">Parking Spots</FormLabel>
                <List >
                    <FlatList
                        data={this.state.spots}
                        renderItem={({item}) => <Text style="leftMargin: 10," >Name: {item.label}</Text>}
                    />
                </List>

                {/*<Text> {JSON.stringify(this.state.spots)} </Text>*/}
            </View >
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        // alignItems: 'center',
        justifyContent: 'center',
    },
    title: {
        fontSize: 35,
        marginLeft: 10,
        marginBottom: 30,
        fontWeight: 'bold',
        color: '#f8971d'
    },
    button: {
        fontSize: 15,
        marginLeft: 10,
        marginBottom: 30,
        fontWeight: 'bold',
        color: '#f8971d'
    }
});
