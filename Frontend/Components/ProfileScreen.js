import React from 'react';
import { StyleSheet, Text, View, TextInput, Image } from 'react-native';
import { MapView, Font, AppLoading } from 'expo';
import { FormLabel, FormInput, Button, List, ListItem } from 'react-native-elements'
import { Ionicons, MaterialCommunityIcons } from '@expo/vector-icons';


export default class ProfileScreen extends React.Component {
  constructor(props) {
    super(props)

  }

  onPressBookmarks = async () =>{
    console.log(global.username);
    await fetch(serverIP + '/GetUserDetails?username=' + global.username, {
      method: 'GET',
      headers: {
        'Token': global.authKey,
        'Accept': 'application/x-www-form-urlencoded',
        'Content-Type': 'application/x-www-form-urlencoded',
      },
    })
      .then(function (response) {
        // Handle HTTP response
        if (response.status.toString() == 200) {
          // console.log(respons)
          console.log(response.bo());
        }
        else {
          return;
        }
      })
      .catch((error) => {
        //
        Alert.alert(error.message);
      });
  }
  // Takes user back to search page


  render() {
    return (
      <View style={styles.container}>
        <View style={{ height: '3%' }} />
        {/* <Text onPress= {() => this.props.navigation.pop()}> back to Search</Text> */}
        <Text style={styles.title} onPress={() => this.onPressBookmarks()} > Hello  {global.username} </Text>

        {/* General User Info */}
        <Text style={styles.category} onPress={() => this.onPressBookmarks()}>Email</Text>


        <Text style={styles.category} onPress={() => this.props.navigation.push('FriendsScreen')}>Followers </Text>

        <Text style={styles.category}>Default Location</Text>

        <Text style={styles.category}>User Preferences</Text>
      </View>

    )
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
    // alignItems: 'center',
    // justifyContent: 'center',
    paddingLeft: 10,
  },
  title: {
    // textAlign: 'center',
    fontSize: 35,
    marginBottom: 30,
    fontWeight: 'bold',
    color: '#f8971d',
    marginTop: '5%'
  },
  category: {
    marginBottom: 10,
    color:'gray',
    fontWeight: 'bold',
    fontSize: 20,
    paddingLeft:5
  }
});
