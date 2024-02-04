import React from "react";
import { invoke } from "@tauri-apps/api/tauri";

import { InputText } from "primereact/inputtext";
import { Fieldset } from "primereact/fieldset";
import { Button } from "primereact/button";
import { Dialog } from "primereact/dialog";

//TODO: the object returned by 'file_blaster' should contain a header object and a list of frames
class DataHeader {
  log_version: number;
  start_time: Date; // This is in UTC
  team_number: string;

  constructor(log_version: number, start_time: string, team_number: string) {
    this.log_version = log_version;
    this.start_time = new Date(start_time);
    this.team_number = team_number;
  }
}

function FilePicker(props: { setData: React.Dispatch<React.SetStateAction<DataHeader | undefined>>, setErr: React.Dispatch<React.SetStateAction<string>> }) {
  const [path, setPath] = React.useState("");

  return <Fieldset legend="Select File">
    <InputText onChange={(e) => setPath(e.target.value)} />
    <Button onClick={_ =>
      invoke('file_blaster', { filePath: path })
        .then((v: any) => props.setData(new DataHeader(v.log_version, v.start_time, v.team_number)))
        .catch(err => props.setErr(err))
    }
      label="Select" />
  </Fieldset>;
}

function DataPreview(props: { data: DataHeader | undefined }) {
  return <div>
    Log version: {props.data?.log_version}<br />
    Date: {props.data?.start_time.toLocaleDateString()}<br />
    Team number: {props.data?.team_number}
  </div>;
}

function ErrorModal(props: { err: string, setErr: React.Dispatch<React.SetStateAction<string>> }) {
  return <Dialog visible={props.err != ""} onHide={() => props.setErr("")} header="Error">
    {props.err}
  </Dialog>;
}


export default function App() {
  //TODO: Make a file upload input instead of a text box.

  const [data, setData] = React.useState<DataHeader>();
  const [err, setErr] = React.useState<string>("");

  return <div>
    <FilePicker setData={setData} setErr={setErr} />
    <ErrorModal err={err} setErr={setErr} />
    <DataPreview data={data} />
  </div>;
}