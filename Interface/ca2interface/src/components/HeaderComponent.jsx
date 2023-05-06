import React, { Component } from "react";
import { Link } from "react-router-dom";

class HeaderComponent extends Component {
  render() {
    return (
      <div>
        <nav className="navbar">
          <Link
            to="/"
            className="navbar-brand mx-3"
            style={{
              fontFamily: '"Open Sans", sans-serif',
              fontSize: "28px",
              color: "rgb(34,43,69)",
            }}
          >
            Tag Clouds
          </Link>
          <ul
            className="navbar-nav"
            style={{ display: "flex", flexDirection: "row", gap: "15px" }}
          >
            <li className="nav-item">
              <Link className="nav-link" to="/global">
                Global Tag Cloud
              </Link>
            </li>
            <li style={{borderLeft:'2px solid rgb(237, 241, 247)'}}></li>
            <li className="nav-item">
              <Link className="nav-link" to="/freq">
                Document Frequency
              </Link>
            </li>
          </ul>
        </nav>
      </div>
    );
  }
}

export default HeaderComponent;
