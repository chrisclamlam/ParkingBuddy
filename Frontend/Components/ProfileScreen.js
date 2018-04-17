import React from 'react';
import { StyleSheet, Text, View, TextInput, Image} from 'react-native';
import { MapView,  Font, AppLoading } from 'expo';
import { FormLabel, FormInput, Button,List,ListItem } from 'react-native-elements'
import { Ionicons, MaterialCommunityIcons } from '@expo/vector-icons';


export default class ProfileScreen extends React.Component {
  constructor(props) {
    super(props)

  }
  
  
  render() {
    return (
      <View style={styles.container}>
      <View style={{height:'3%'}}/>
        {/* <Text onPress= {() => this.props.navigation.pop()}> back to Search</Text> */}
        <Text style={styles.title} > Hello Name! </Text>


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
