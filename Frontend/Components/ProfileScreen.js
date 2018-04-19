import React from 'react';
import { StyleSheet, Text, View, TextInput, Image, ScrollView } from 'react-native';
import { MapView, Font, AppLoading } from 'expo';
import { FormLabel, FormInput, Button, List, ListItem } from 'react-native-elements'
import { Ionicons, MaterialCommunityIcons } from '@expo/vector-icons';


export default class ProfileScreen extends React.Component {
  constructor(props) {
    super(props)

    this.state = {
      username: "",
      email: "",
      fname: "",
      lname: "",
      friends: [],
      spots: []
    }

  }

  onPressBookmarks = async () => {
    // console.log(global.username);
    try {
      let response = await fetch(global.serverIP + 'GetUserDetails?username=' + global.username, {
        method: 'GET',
        headers: {
          'Token': global.authKey,
          'Accept': 'application/x-www-form-urlencoded',
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        timeout: 10,
        // body: coords
      });

      // console.log("response=   "+  response);
      let responseJson = await response.json();

      if (response.status == 200) { // Request is good and there are results
        console.log(responseJson);

        this.setState({
          username: responseJson.user.username, email: responseJson.user.email, fname: responseJson.user.fname, lname: responseJson.user.lname,
          friends: responseJson.friends, spots: responseJson.spots
        });
        console.log("username = " + this.state.spots)

      }
      else { // No results
        Alert.alert("Unable to find any location");
      }

    } catch (error) {
      console.error(error);

    }






    // .then(function (response) {
    //   // Handle HTTP response

    //   let responseJson = response.json();


    //   if (response.status.toString() == 200) {
    //     // console.log(respons)
    //     console.log(responseJson);
    //   }
    //   else {
    //     return;
    //   }
    // })

  }


  componentWillMount() {
    this.onPressBookmarks();

  }



  render() {


    const ownProfile = (this.state.username == global.username);
    const button = ownProfile ? (<View />) : (<Ionicons
      style={{ marginLeft: '90%', marginTop: '10%' }}
      onPress={() => { this.props.navigation.push('ProfileScreen') }}
      name="md-person-add" size={32} color="gray" />);




    return (
      <View style={styles.container}>
        <View style={{ height: '3%' }} />






        {/* <Text onPress= {() => this.props.navigation.pop()}> back to Search</Text> */}
        <Text style={styles.title}  > Hello  {this.state.username} </Text>
        {/* {button} */}
        <ScrollView>
        <FormLabel labelStyle={{ color: '#f8971d' }}>First Name</FormLabel>
        <FormInput value={this.state.fname} />
        <FormLabel labelStyle={{ color: '#f8971d' }}>Last Name</FormLabel>
        <FormInput value={this.state.lname} />
        <FormLabel labelStyle={{ color: '#f8971d' }}>Email</FormLabel>
        <FormInput value={this.state.email} />

        <FormLabel labelStyle={{ color: '#f8971d' }}>Friends</FormLabel>
        {console.disableYellowBox = true}
        {this.state.friends.map((friend) => {
          //  var myfriend = this.state.friends;
          return (

            <FormInput value={friend} />
          );
        })}

          <FormLabel labelStyle={{ color: '#f8971d' }}>Spots</FormLabel>
          {this.state.spots.map((spots) => {
            //  var myfriend = this.state.friends;
            var region = {longitude: spots.longitude,
              latitude:spots.latitude,
              latitudeDelta: 0.072,
              longitudeDelta: 0.0221,
            }
            return (
              <View>
              <FormInput value={spots.label} />
              <MapView 
              initialRegion={region}
              style={{ height:300}}
              

              />
              </View>
            );
          })}

          <View style={{height: 200}}/>


</ScrollView>

      </View>

    )
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
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
    color: 'black',
    fontWeight: 'bold',
    fontSize: 20,
    paddingLeft: 5
  },
  text: {
    marginBottom: 10,
    color: 'gray',
    fontWeight: 'bold',
    fontSize: 20,
    paddingRight: 5
  },

});
