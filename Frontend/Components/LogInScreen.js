import React from 'react';
import { StyleSheet, Text, View, TextInput, Image } from 'react-native';
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




    this.props.navigation.push('SearchLocationScreen')
  }

  onPressSignUp() {
    this.props.navigation.push('SignUpScreen')
  }





  render() {
    return (
      <View style={styles.container}>

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
        <Button
          buttonStyle={{ borderRadius: 10, backgroundColor: 'transparent' }}
          fontSize={15}
          color='white'
          onPress={() => this.props.navigation.push('SearchLocationScreen')} title="Continue as guest" />



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
