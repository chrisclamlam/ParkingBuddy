import React from 'react';
import { StyleSheet, Text, View, TextInput, Alert} from 'react-native'
import { MapView } from 'expo';

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
        fetch('http://172.20.10.4:8080/ParkingBuddy/SignUp', {
            method: 'POST',
            headers: {
                'Accept': 'application/x-www-form-urlencoded',
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            timeout: 10,
            body: paramInput
        })
            .then(function(response){
                // Handle HTTP response
                if(response.status.toString() == 200){
                    // Save this in a global variable, locally on filesystem is slow
                    //response.headers.get('Set-Cookie'); // Gets Bryce's token
                    Alert.alert("Successful Sign Up!");
                }
                else{
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
       <Text> SIGN UP SCREEN </Text>
          <View style={styles.formError}></View>
          <TextInput
              style={{margin: '1%', width: '80%', height: '5%', borderColor: 'gray', borderWidth: 1}}
              onChangeText={(text) => (this.setState({username: text}))}
              placeholder={'Enter Username'}
          />
          <TextInput
              style={{margin: '1%', width: '80%', height: '5%', borderColor: 'gray', borderWidth: 1}}
              placeholder={'Enter Password'}
              onChangeText={(text) => (this.setState({password: text}))}
          />
          <TextInput
              style={{margin: '1%', width: '80%', height: '5%', borderColor: 'gray', borderWidth: 1}}
              placeholder={'Enter First Name'}
              onChangeText={(text) => (this.setState({fname: text}))}
          />
          <TextInput
              style={{margin: '1%', width: '80%', height: '5%', borderColor: 'gray', borderWidth: 1}}
              placeholder={'Enter Last Name'}
              onChangeText={(text) => (this.setState({lname: text}))}
          />
          <TextInput
              style={{margin: '1%', width: '80%', height: '5%', borderColor: 'gray', borderWidth: 1}}
              placeholder={'Enter E-mail'}
              onChangeText={(text) => (this.setState({email: text}))}
          />

          <Text onPress={() => this.verifyInput(this)}> Register</Text>

          <Text onPress={() => this.props.navigation.pop()}> Go back to login </Text>
      </View >
    );
  }
}



const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
