import React from "react";
import { invoke } from "@tauri-apps/api/tauri";

import { InputText } from "primereact/inputtext";

export default function App() {
  //TODO: Make a file upload input instead of a text box.
  return <div>
    <InputText onChange={(e) => invoke('file_blaster', {path: e.target.value})}/>
  </div>;
}