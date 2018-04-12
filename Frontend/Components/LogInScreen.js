import React from 'react';
import { StyleSheet, Text, View, TextInput } from 'react-native';
import { MapView } from 'expo';
import { FormLabel, FormInput, Button } from 'react-native-elements'


export default class LogInScreen extends React.Component {
  constructor(props) {
    super(props)

    this.state = {
      inputUsername: '',
      inputPassword: ''
    }
  }

  async onPressLogIn() {




    this.props.navigation.push('MapScreen')
  }

  onPressSignUp() {
    this.props.navigation.push('SignUpScreen')
  }





  render() {
    return (
      <View style={styles.container}>

        {/* TITLE */}
        <View style={{ alignItems: 'center' }}>
          <Text style={{ marginBottom: 40, fontSize: 20, fontWeight: 'bold' }}> WELCOME TO PARKING BUDDY </Text>
        </View>




        <FormLabel>Username</FormLabel>
        <FormInput
          inputStyle={{ color: 'black' }}
          onChangeText={(text) => (this.setState({ inputUsername: text }))} />
        <FormLabel>Password</FormLabel>
        <FormInput
          inputStyle={{ color: 'black' }}
          secureTextEntry onChangeText={(text) => (this.setState({ inputPassword: text }))} />



        <View style={{ height: 80 }} />

        {/* LOGIN/REGISTER/CONTINUE AS GUEST BUTTONS */}
        <Button
          buttonStyle={{ borderRadius: 10, backgroundColor: 'rgb(76,217,100)', width: '100%' }}
          title=" LogIn "
          onPress={() => this.onPressLogIn()} />
        <View style={{ height: 10 }} />
        <Button
          buttonStyle={{ borderRadius: 10, backgroundColor: 'rgb(0,122,255)', width: '100%' }}
          onPress={() => this.onPressSignUp()} title="Register" />
        <Button
          buttonStyle={{ borderRadius: 10, backgroundColor: 'transparent' }}
          fontSize={15}
          color='rgb(0,122,255)'
          onPress={() => this.props.navigation.push('MapScreen')} title="Continue as guest" />



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
});
