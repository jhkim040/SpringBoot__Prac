export const INCREMENT = "INCREMENT";
export const DECREMENT = "DECREMENT";

// action
export const increase = (username) => ({
  type: INCREMENT,
  payload: username,
});
export const decrease = () => ({
  type: DECREMENT,
});

// state
const initState = {
  username: "",
  number: 1,
};

// reducer
const reducer = (state = initState, action) => {
  switch (action.type) {
    case INCREMENT:
      return { number: state.number + 1, username: action.payload };
    case DECREMENT:
      return { number: state.number - 1 };
    default:
      return state;
  }
};

export default reducer;
