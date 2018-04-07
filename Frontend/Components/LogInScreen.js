import React from 'react';
import { StyleSheet, Text, View, TextInput, Button } from 'react-native';
import { MapView } from 'expo';

export default class LogInScreen extends React.Component {
  constructor(props) {
    super(props)

    this.state = {
      inputUsername: '',
      inputPassword: ''
    }
  }
  render() {
    return (
      <View style={styles.container}>

        {/* TITLE */}
        <Text style={{ marginBottom: 40, fontSize: 20, fontWeight: 'bold' }}> WELCOME TO PARKING BUDDY </Text>

        {/* USERNAME/PASSWORD FORMS */}
        <TextInput
          style={{ height: 40, borderColor: 'gray', borderWidth: 1, borderRadius: 10, padding: 10, width: '80%' }}
          onChangeText={(text) => this.setState({ inputUsername: text })}
          placeholder='Username'
        />

        {/* BLANK SPACE */}
        <View style={{ height: 20 }} />
        <TextInput
          style={{ height: 40, borderColor: 'gray', borderWidth: 1, borderRadius: 10, padding: 10, width: '80%' }}
          onChangeText={(text) => this.setState({ inputPassword: text })}
          placeholder='Password'
        />

        {/* LOGIN/REGISTER/CONTINUE AS GUEST BUTTONS */}
        <Button onPress={() => this.props.navigation.push('HomeScreen')} title="LogIn" />
        <Button onPress={() => this.props.navigation.push('SignUpScreen')} title="Register" />
        <Button onPress={() => this.props.navigation.push('HomeScreen')} title="Continue as guest" />




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
