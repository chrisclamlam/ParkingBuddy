import React from 'react';
import { StyleSheet, Text, View, TextInput, Image} from 'react-native';
import { MapView,  Font, AppLoading } from 'expo';
import { FormLabel, FormInput, Button,List,ListItem } from 'react-native-elements'
import { Ionicons, MaterialCommunityIcons } from '@expo/vector-icons';


export default class ProfileScreen extends React.Component {
  constructor(props) {
    super(props)

  }

  // Takes user back to search page

  
  render() {
    return (
      <View style={styles.container}>
      <View style={{height:'3%'}}/>
        {/* <Text onPress= {() => this.props.navigation.pop()}> back to Search</Text> */}
        <Text style={styles.title} > Hello  {global.username} </Text>

        {/* General User Info */}
        <View>
          <FormLabel>Bookmarks</FormLabel>

          <FormLabel>Email</FormLabel>

          <FormLabel>Default Location</FormLabel>

          <FormLabel>User Preferences</FormLabel>
        </View>

        {/* Buttlson to take User to Search Page */}
        {/* <View>
          <Button onclick={() => toSearch()}>Search</Button>
        </View> */}
      </View >
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
    // alignItems: 'center',
    // justifyContent: 'center',
    marginLeft: 10,
  },
  title: {
    // textAlign: 'center',
    fontSize: 35,
    marginBottom: 30,
    fontWeight: 'bold',
    color: '#f8971d',
    marginTop: '5%'
},
});
