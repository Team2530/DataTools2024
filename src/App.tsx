import React from "react";
import { invoke } from "@tauri-apps/api/tauri";

import { InputText } from "primereact/inputtext";
import { Fieldset } from "primereact/fieldset";
import { Button } from "primereact/button";

export default function App() {
  //TODO: Make a file upload input instead of a text box.

  const [path, setPath] = React.useState("");

  return <Fieldset legend="Select File">
    <InputText onChange={(e) => setPath(e.target.value)}/>
    <Button onClick={e => console.log(invoke('file_blaster', {filePath: path}))} />
  </Fieldset>;
}