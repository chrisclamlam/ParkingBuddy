# react-native-searchbar

A pretty awesome search bar for React Native. It has a built in simple search capability which can be utilised by handing the component `data` and providing a `handleResults` function which will receive the filtered copy of the `data` array.

The search function uses a depth-first algorithm to traverse the data and simply looks for an `indexOf` the user input within all strings, numbers, and boolean values in the data.

Works on both iOS and Android.

![react-native-searchbar](http://i.imgur.com/i21YwnW.gif)

(Rendered with the example in these docs and repository)

## Installation

- `npm install react-native-searchbar --save`
- Install [`react-native-vector-icons`](https://github.com/oblador/react-native-vector-icons) if the project doesn't have them already. The search bar accesses MaterialIcons.
- Now you can require the search bar with  `import SearchBar from 'react-native-searchbar'` or `var SearchBar = require('react-native-searchbar')`

## Available Props

Property	|	Type		|	Default		|	Description |
:---------|:--------|:----------|:------------|
data	|	array	|	`[]`	|	The array of data to be searched (if not using a custom search algorithm)
placeholder	|	string	|	`Search`	|	The placeholder for the search bar.
handleChangeText(input) | function | `null` | Fires when the input text changes.
handleSearch(input) | function | `null` | Fires after `handleChangeText`, and can be used to implement a custom search. If not present, the internal search logic will be used.
handleResults(results) | function | `null` | Fires after each internal search with the results array.
onSubmitEditing | function | `null` | Fired after pressing 'search' on the device keyboard
getValue | function | `null` | Returns the current value of the search bar input.
onHide(input) | function | `null` | Fires when the search bar is hidden with the current user input.
onBack | function | Hides the search bar | Fires when the back icon is pressed.
onX | function | `null` | Fires when the X button is pressed. Still clears the input.
onFocus | function | `null` | Fires when the search bar receives focus.
onBlur | function | `null` | Fires when the search bar loses focus.
backButton | Component | MaterialIcon | Sets the back button component.
backButtonAccessibilityLabel | string| `Navigate up` | Accessibility label for the back button.
closeButton | Component | MaterialIcon | Sets the close button component.
closeButtonAccessibilityLabel | string | `Clear search text` | Accessibility label for the close button.
backCloseSize | number | 28 | Sets the size of back button & close button.
heightAdjust | number | `0` | Adjust the height of the search bar.
backgroundColor | string | `white` | The background colour of the search bar.
iconColor | string | `gray` | The color of the back and X icons.
textColor | string | `gray` | The color of the search bar text.
selectionColor | string | `lightskyblue` | The color of the the search bar cursor and text selection.
placeholderTextColor | string | `lightgray` | The color of the placeholder text.
animate | boolean | `true` | Animate the search bar when it is shown and hidden.
animationDuration | number | `200` | The duration of the above animation in milliseconds.
showOnLoad | boolean | `false` | Show the search bar when it is first rendered.
hideBack | boolean | `false` | Hide the back button.
hideX | boolean | `false` | Hide the X icon which clears the input.
iOSPadding | boolean | `true` | Pad the size of the iOS status bar.
iOSHideShadow | boolean | `false` | Hide the shadow under the search bar in iOS.
clearOnShow | boolean | `false` | Clear input when the search bar is shown.
clearOnHide | boolean | `true` | Clear input when the search bar is hidden.
clearOnBlur | boolean | `false` | Clear input when the search bar is blurred.
focusOnLayout | boolean | `true` | Focus the text input box whenever it is shown.
autoCorrect | boolean | `true` | AutoCorrect the search bar text.
autoCapitalize | string | `sentences` | Auto capitialize behaviour of the text input - enum('none', 'sentences', 'words', 'characters')
keyboardAppearance | string | 'default' | Determines the color of the keyboard.
fontFamily | string | `System` | The font family to be used.
fontSize | number | 20 | Sets the font size.
allDataOnEmptySearch | boolean | `false` | Search results behave as a `.filter`, returning all data when the input is an empty string.



## Usage

- Use a ref to show and hide the search bar `ref={(ref) => this.searchBar = ref}`
	- `this.searchBar.show()`
	- `this.searchBar.hide()`

- Write your own search logic with `handleSearch` or provide some `data` and use the results handed back from `handleResults`.
- Use your powers for good!


##### Notes for Android

- Render the search bar component after the component it is supposed to display over. iOS handles this nicely with a `zIndex` of `10`. Android elevation is set to 2.
- The bottom of the search bar will have a thin border instead of a shadow.



## Example

Full example at `example/`

```javascript
import SearchBar from 'react-native-searchbar';

const items = [
  1337,
  'janeway',
  {
    lots: 'of',
    different: {
      types: 0,
      data: false,
      that: {
        can: {
          be: {
            quite: {
              complex: {
                hidden: [ 'gold!' ],
              },
            },
          },
        },
      },
    },
  },
  [ 4, 2, 'tree' ],
];

...
_handleResults(results) {
  this.setState({ results });
}
...

...
<SearchBar
  ref={(ref) => this.searchBar = ref}
  data={items}
  handleResults={this._handleResults}
  showOnLoad
/>
...
```

## Contributing

Contributing to `react-native-searchbar` is easy! With four simple steps:

### Create a branch

1. Fork the repository
1. `git clone <your-repo-url>` to clone your GitHub repo to your local one
1. `git pull origin master` to pull the latest code
1. `npm install` to install the project's dependencies
1. `git checkout -b the-name-of-my-branch` to create a branch (use something short and comprehensible, such as: `fix-styling-of-search-bar`).
  
### Make the change

### Test the change
1. Run `npm run fix` from the project root (This will run Prettier and ESLint and automatically fix any issues).
1. If possible, test any visual changes in Android and iOS.

### Push the change!
1. `git add -A && git commit -m "My message"` (replacing `My message` with a commit message, such as `Fixed styling on search bar`) to stage and commit your changes
1. `git push my-fork-name the-name-of-my-branch`
