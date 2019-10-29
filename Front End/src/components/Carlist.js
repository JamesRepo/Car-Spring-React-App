import React, { Component } from 'react';
import { SERVER_URL } from '../constants.js';
import ReactTable from "react-table";
import 'react-table/react-table.css';
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';
import AddCar from './AddCar.js';
import { thisTypeAnnotation } from '@babel/types';
import { CSVLink } from 'react-csv';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import Snackbar from '@material-ui/core/Snackbar';

class Carlist extends Component {
    
    constructor(props) {
        super(props);
        this.state = { cars: [], open: false, message: '' };
    }

    componentDidMount() {
        this.fetchCars();
    }

    fetchCars = () => {
        fetch(SERVER_URL + 'api/cars')
        .then((response) => response.json())
        .then((responseData) => {
            this.setState({
                cars: responseData._embedded.cars,
            });
        })
        .catch(err => console.error(err));
    }

    onDelClick = (link) => {
        fetch(link, {method: 'DELETE'})
        .then(res => {
            this.setState({open: true, message: 'Car deleted'});
            this.fetchCars();
        })
        .catch(err => {
            this.setState({open: true, message: 'Error deleting'});
            console.error(err);
        })
    }

    confirmDelete = (link) => {
        confirmAlert({
            message: 'Are you sure you want to delete?',
            buttons: [
                {
                    label: 'Yes',
                    onClick: () => this.onDelClick(link)
                },
                {
                    label: 'No'
                }
            ]
        })
    }

    addCar(car) {
        fetch(SERVER_URL + 'api/cars',
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(car)
        })
        .then(res => this.fetchCars())
        .catch(err => console.error(err))
    }

    renderEditable = (cellInfo) => {
        return (
            <div style={{ backgroundColor: "#fafafa" }} contentEditable suppressContentEditableWarning onBlur={e => {
                const data = [...this.state.cars];
                data[cellInfo.index][cellInfo.column.id] = e.target.innerHTML;
                this.setState({ cars: data });
            }} dangerouslySetInnerHTML={{
                __html: this.state.cars[cellInfo.index][cellInfo.column.id]
            }} />
        )
    }

    updateCar(car, link) {
        fetch(link, 
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(car)
            })
            .then(res => this.setState({open: true, message: 'Changes saved'})
        )
        .catch(err => this.setState({open: true, message: 'Error when saving'})
        )
    }

    handleClose = (event, reason) => {
        this.setState({open:false});
    }

    render() {
        const columns = [{
            Header: 'Brand',
            accessor: 'brand',
            Cell: this.renderEditable
        }, {
            Header: 'Model',
            accessor: 'model',
            Cell: this.renderEditable
        }, {
            Header: 'Colour',
            accessor: 'color',
            Cell: this.renderEditable
        }, {
            Header: 'Year',
            accessor: 'year',
            Cell: this.renderEditable
        }, {
            Header: 'Price £',
            accessor: 'price',
            Cell: this.renderEditable
        }, {
            id: 'savebutton',
            sortable: false,
            filterable: false,
            width: 100,
            accessor: '_links.self.href',
            Cell: ({value, row}) => (<Button size="small" variant="flat" color="primary" onClick={() => {this.updateCar(row, value)}}>Save</Button>)
        }, {
            id: 'delbutton',
            sortable: false,
            filterable: false,
            width: 100,
            accessor: '_links.self.href',
            Cell: ({value}) => (<Button size="small" variant="flat" color="secondary" onClick={()=>{this.confirmDelete(value)}}>Delete</Button>)
        }]
        return (
            <div className="App">
                <Grid container>
                    <Grid item>
                        <AddCar addCar={this.addCar} fetchCars={this.fetchCars} />
                    </Grid>
                    <Grid item style={{padding: 20}}>
                        <CSVLink data={this.state.cars} separator=";">Export CSV</CSVLink>
                    </Grid>
                </Grid>
                <ReactTable data={this.state.cars} columns={columns} filterable={true} />
                <Snackbar style={{width: 300, color: 'green'}} open={this.state.open} onClose={this.handleClose} autoHideDuration={1500} message={this.state.message} />
            </div>
        );
    }
}

export default Carlist;