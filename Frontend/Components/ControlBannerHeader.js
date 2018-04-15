import React from 'react';
import {
  Alert,
  Container,
  StyleSheet,
  KeyboardAvoidingView,
  Button,
  Text,
  View,
  Image,
  TouchableHighlight,
} from 'react-native';
import { Icon } from 'native-base'

export default class ControlBanner extends React.Component {

  //need to change the images to buttons with icons
  render() {
    return (
      <View style={StyleSheet.flatten([styles.leftRight, { marginTop: 0, marginBottom: 0, height: 60 }])}>
        <TouchableHighlight>
          {/* <Text style={{paddingTop: 27, paddingLeft: 15,fontSize: 20, color: 'transparent', fontWeight: 'bold'}}>BuzzFeed </Text> */}
          <Icon name="md-menu" style={{ paddingLeft: 15, paddingTop: 40, transform: [{ scaleX: 0.8 }, { scaleY: 0.8 }], color: 'gray', marginBottom: 7 }}
          />
        </TouchableHighlight>
        <TouchableHighlight>
          {/* <Text style={{paddingTop: 27, paddingRight: 0,fontSize: 20, color: 'red', fontWeight: 'bold'}}> BuzzFeed</Text> */}
          <Image source={require('../images/logo.png')} style={{ transform: [{ scaleX: 0.6 }, { scaleY: 0.6 }], marginTop: 30, marginLeft: 90, marginBottom: 7 }} />
        </TouchableHighlight>
        <Image
          style={{ width: 60, height: 20, alignSelf: 'center' }} />
        <TouchableHighlight onPress={() => { this.props.topLevelNavigator.push('EmptyMap') }}>
          <Icon name="md-compass" style={{ paddingRight: 15, paddingTop: 40, transform: [{ scaleX: 0.8 }, { scaleY: 0.8 }], color: 'gray', marginBottom: 7 }}
          />
        </TouchableHighlight>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  headerText: {
    color: '#fff',
    fontSize: 18,
    fontWeight: '600',
  },
  leftRight: {
    padding: 0,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  banner: {

    fontWeight: '600',
    color: '#fff',
  },
});
