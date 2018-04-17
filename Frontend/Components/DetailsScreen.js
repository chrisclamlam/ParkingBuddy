import React from 'react';
import { StyleSheet, Text, View, TextInput, Image, Alert, ScrollView, } from 'react-native';
import { MapView } from 'expo';
import { FormLabel, FormInput, Button, List, ListItem,  } from 'react-native-elements'


export default class ProfileScreen extends React.Component {
    constructor(props) {
        super(props)

        // Variables for current page
        this.state = {
            test: '',
        }
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
                    </View>

                    <View>
                        { /* Options for logged in users
                            - Add parking structure to favorites list
                            - Save search query
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
