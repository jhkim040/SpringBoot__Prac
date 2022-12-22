import kakaoLogo from "../images/kakao_login_medium_narrow.png";

const Login = () => {
  const btnStyle = {
    width: "200px",
    height: "50px",
    background: `url(${kakaoLogo}) no-repeat center center`,
    backgroundSize: "contain",
    cursor: "pointer",
    border: "none",
  };
  const CLIENT_ID = ""; // REST API KEY
  const REDIRECT_URI = ""; // redirect uri
  return (
    <div>
      <button
        style={btnStyle}
        onClick={() => {
          window.location.href = `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;
        }}
      ></button>
    </div>
  );
};
export default Login;
