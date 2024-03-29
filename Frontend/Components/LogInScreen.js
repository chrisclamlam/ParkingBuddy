import React from 'react';
import { StyleSheet, Text, View, TextInput, Image, Alert } from 'react-native';
import { MapView } from 'expo';
import { FormLabel, FormInput, Button } from 'react-native-elements'


export default class LogInScreen extends React.Component {
    constructor(props) {
        super(props)

        this.state = {
            inputUsername: '',
            inputPassword: '',
        }
    }

    // Authenticate login
    async onPressLogIn() {
        // Set up parameters to send to servlet
        var params = "username=" + this.state.inputUsername + "&passhash=" + this.state.inputPassword;
        // For testing
        // this.props.navigation.push('SearchLocationScreen');
        // Fetch to our login servlet
        await fetch(serverIP + '/Login', {
            method: 'POST',
            headers: {
                'Accept': 'application/x-www-form-urlencoded',
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: params
        })
            .then(function (response) {
                // Handle HTTP response
                if (response.status.toString() == 200) {
                    // Save this in a global variable, locally on filesystem is slow
                    //response.headers.get('Set-Cookie'); // Gets Bryce's token
                    // Alert.alert("Successful Log-In!");
                    global.loggedIn = true;
                    console.log("dsfdsfdsf" + response.headers.get('Set-Cookie'));
                    global.authKey = response.headers.get('Set-Cookie');

                }
                else {
                    Alert.alert("Username/Password invalid. Please try again. ");
                    global.username = "guest";
                    return;
                }
            })
            .catch((error) => {
                //
                Alert.alert(error.message);
            });

        if (global.loggedIn) {
            this.props.navigation.push('SearchLocationScreen');
            global.username = this.state.inputUsername;
        }

    }

    // Directs user to sign up page
    onPressSignUp() {
        this.props.navigation.push('SignUpScreen')
    }

    // Checks to see if user is logged in already
    checkLogin = () => {
        if (global.loggedIn == true) {
            Alert.alert("You're logged in!");
            this.props.navigation.push('SearchLocationScreen');

        }
    }

    continueGuest() {
        global.loggedIn = false;
        global.username = "Guest";
        this.props.navigation.push('SearchLocationScreen')
    }



    render() {
        return (
            <View onload={() => this.checkLogin(this)} style={styles.container}>

                {/* TITLE */}
                <View style={{ flex: 1, alignItems: 'center' }}>
                    {/* <Text style={{ marginBottom: 40, fontSize: 30, fontWeight: 'bold' }}>Parking Buddy </Text> */}
                    <Image style={{ flex: 1, alignItems: 'center', aspectRatio: 0.5, resizeMode: 'contain' }} source={require('../images/logo.png')} />
                </View>

                <FormLabel labelStyle={{ color: '#f8971d' }}>Username</FormLabel>
                <FormInput

                    inputStyle={{ color: 'gray' }}
                    onChangeText={(text) => (this.setState({ inputUsername: text }))} />
                <FormLabel labelStyle={{ color: '#f8971d' }}>Password</FormLabel>
                <FormInput
                    inputStyle={{ color: 'gray' }}
                    secureTextEntry onChangeText={(text) => (this.setState({ inputPassword: text }))} />



                <View style={{ height: 50 }} />

                {/* LOGIN/REGISTER/CONTINUE AS GUEST BUTTONS */}
                <Button
                    buttonStyle={{ borderRadius: 10, backgroundColor: '#f8971d', width: '100%' }}
                    title=" LogIn "
                    onPress={() => this.onPressLogIn()} />
                <View style={{ height: 10 }} />
                <Button
                    buttonStyle={{ borderRadius: 10, backgroundColor: 'transparent', width: '100%' }}
                    textStyle={{ color: 'gray', fontSize: 15, marginTop: -15 }} onPress={() => this.onPressSignUp()} title="Register" />
                {/* <Text style={{alignContent:'center', justifyContent:'center'}}> fdsafds</Text> */}
                <Button
                    buttonStyle={{ borderRadius: 10, backgroundColor: 'transparent', marginTop: -20 }}
                    fontSize={15}
                    color='gray'
                    onPress={() => this.continueGuest()} title="Continue as guest" />



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
