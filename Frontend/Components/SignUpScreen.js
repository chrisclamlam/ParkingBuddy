import React from 'react';
import { StyleSheet, Text, View, TextInput, Alert } from 'react-native'
import { MapView } from 'expo';
import { FormLabel, FormInput, Button } from 'react-native-elements'

export default class SignUpScreen extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
            fname: "",
            lname: "",
            email: ""
        }
    }

    // Send user data to database to be authenticated
    // If user data is valid, the database will create user
    // Else, the user will be told that sign up was unsuccessful

    verifyInput = () => {
        // Prepare user input to send to servlet
        const paramInput = 'fname=' + this.state.fname +
            '&lname=' + this.state.lname +
            '&username=' + this.state.username +
            '&password=' + this.state.password +
            '&email=' + this.state.email;

        // Fetch to our servlet: sending the user form data as the body
        // Bryce has his authen token in response header as "Set-Cookie": token
        fetch('http://10.123.112.238:8080/ParkingBuddy/SignUp', {
            method: 'POST',
            headers: {
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
                <Text style={styles.title} > Sign Up </Text>
                <View style={styles.formError}></View>


                <FormLabel>Username</FormLabel>
                <FormInput onChangeText={(text) => (this.setState({ username: text }))} />
                <FormLabel>Password</FormLabel>
                <FormInput secureTextEntry onChangeText={(text) => (this.setState({ password: text }))} />
                <FormLabel>First Name</FormLabel>
                <FormInput onChangeText={(text) => (this.setState({ fname: text }))} />
                <FormLabel>Last Name</FormLabel>
                <FormInput onChangeText={(text) => (this.setState({ lname: text }))} />
                <FormLabel>Email</FormLabel>
                <FormInput onChangeText={(text) => (this.setState({ email: text }))} />

                <View style={{ height: 80 }} />
                <Button
                    buttonStyle={{ borderRadius: 10, backgroundColor: 'rgb(76,217,100)', width: '100%' }}
                    onPress={() => this.verifyInput(this)} title="Register" />

                <Button
                    buttonStyle={{ borderRadius: 10, backgroundColor: 'transparent' }}
                    fontSize={15}
                    color='rgb(0,122,255)'
                    onPress={() => this.props.navigation.pop()} title="Back to Login" />




                {/* <TextInput
                    style={styles.textFields}
                    onChangeText={(text) => (this.setState({ username: text }))}
                    placeholder={'Enter Username'}
                />
                <TextInput
                    style={styles.textFields}
                    placeholder={'Enter Password'}
                    onChangeText={(text) => (this.setState({ password: text }))}
                />
                <TextInput
                    style={styles.textFields}
                    placeholder={'Enter First Name'}
                    onChangeText={(text) => (this.setState({ fname: text }))}
                />
                <TextInput
                    style={styles.textFields}
                    placeholder={'Enter Last Name'}
                    onChangeText={(text) => (this.setState({ lname: text }))}
                />
                <TextInput
                    style={styles.textFields}
                    placeholder={'Enter E-mail'}
                    onChangeText={(text) => (this.setState({ email: text }))}
                />
                <Text onPress={() => this.verifyInput(this)}> Register</Text>
                <Text onPress={() => this.props.navigation.pop()}> Go back to login </Text> */}




                {/* <FormValidationMessage>Error message</FormValidationMessage> */}
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
        fontWeight: 'bold'
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
