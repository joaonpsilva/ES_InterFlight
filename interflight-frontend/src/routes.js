
import Dashboard from "views/Dashboard.js";
import Notifications from "views/Notifications.js";
import TableList from "views/Tables.js";

var routes = [
  {
    path: "/dashboard",
    name: "RealTime",
    icon: "nc-icon nc-globe",
    component: Dashboard,
    layout: "/admin",
  },
  {
    path: "/notifications",
    name: "Spotter",
    icon: "nc-icon nc-camera-compact",
    component: Notifications,
    layout: "/admin",
  },

  {
    path: "/tables",
    name: "Historic",
    icon: "nc-icon nc-refresh-69",
    component: TableList,
    layout: "/admin",
  },

];
export default routes;
